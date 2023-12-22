package com.rn00n.kwekwe.security.jwt

import com.rn00n.kwekwe.security.dto.PrincipalDetails
import com.rn00n.kwekwe.security.dto.TokenInfo
import com.rn00n.kwekwe.security.service.DefaultUserDetailsService
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.*

private val logger = KotlinLogging.logger {}
private const val AUTHORIZATION = "Authorization"
private const val TOKEN_TYPE = "Bearer"
private const val ACCESS = "ACCESS"
private const val REFRESH = "REFRESH"

@Component
class JwtTokenProvider(
    @Value("\${security.jwt.secret-key}")
    private val secretKey: String,
    @Value("\${security.jwt.access-token-validity-seconds}")
    private val accessTokenValiditySeconds: Long,
    @Value("\${security.jwt.refresh-token-validity-seconds}")
    private val refreshTokenValiditySeconds: Long,
    private val userDetailsService: DefaultUserDetailsService,
) {

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    /**
     * Token 생성
     */
    fun createTokenInfo(authentication: Authentication): TokenInfo {
        val username = (authentication.principal as PrincipalDetails).username
        // Access Token
        val accessToken = createAccessToken(
            username,
            authentication.authorities.joinToString(",", transform = GrantedAuthority::getAuthority)
        )
        val refreshToken = createRefreshToken(username)

        return TokenInfo(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expired = getClaims(accessToken).expiration.time
        )
    }

    fun createTokenInfo(username: String, authorities: MutableCollection<out GrantedAuthority>): TokenInfo {
        // Access Token
        val accessToken = createAccessToken(
            username,
            authorities.joinToString(",", transform = GrantedAuthority::getAuthority)
        )
        val refreshToken = createRefreshToken(username)

        return TokenInfo(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expired = getClaims(accessToken).expiration.time
        )
    }

    private fun createAccessToken(username: String, authorities: String): String {
        val claims = Jwts.claims().setSubject(username);
        claims["username"] = username
        claims["authorities"] = authorities
        claims["token-type"] = ACCESS

        val now = Date()
        val accessExpiration = Date(now.time + accessTokenValiditySeconds * 1000)

        // Access Token
        return Jwts
            .builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(accessExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun createRefreshToken(username: String): String {
        val claims = Jwts.claims().setSubject(username);
        claims["username"] = username
        claims["token-type"] = REFRESH

        val now = Date()
        val accessExpiration = Date(now.time + refreshTokenValiditySeconds * 1000)

        // Refresh Token
        return Jwts
            .builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(accessExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    /**
     * Token 정보 추출
     */
    fun getAuthentication(token: String): Authentication {
        val claims: Claims = getClaims(token)

//        val auth = claims["authorities"] ?: throw RuntimeException("잘못된 토큰 입니다.")
        val username = claims["username"] ?: throw RuntimeException("잘못된 토큰 입니다.")

        // 권한 정보 추출
//        val authorities: Collection<GrantedAuthority> = (auth as String)
//            .split(",")
//            .map { SimpleGrantedAuthority(it) }

        val principalDetails = userDetailsService.loadUserByUsername(username as String)

        val principal: UserDetails = PrincipalDetails((principalDetails as PrincipalDetails).account)

        return UsernamePasswordAuthenticationToken(principal, "", principal.authorities)
    }

    fun validateRefreshToken(refreshToken: String): Boolean {
        try {
            val claims = getClaims(refreshToken)
            val tokenType = claims["token-type"] ?: throw RuntimeException("잘못된 토큰 입니다.")

            if (tokenType != REFRESH) {
                throw JwtException("Refresh Token 이 아닙니다.")
            }
            return true;
        } catch (e: JwtException) {
            println(e.message)
        }

        return false;
    }

    /**
     * Token 검증
     */
    fun validateAccessToken(token: String): Boolean {
        try {
            val claims = getClaims(token)
            val tokenType = claims["token-type"] ?: throw RuntimeException("잘못된 토큰 입니다.")

            if (tokenType == ACCESS) {
                return true
            }
        } catch (e: Exception) {
            when (e) {
                is SecurityException -> {}  // Invalid JWT Token
                is MalformedJwtException -> {}  // Invalid JWT Token
                is ExpiredJwtException -> {}    // Expired JWT Token
                is UnsupportedJwtException -> {}    // Unsupported JWT Token
                is IllegalArgumentException -> {}   // JWT claims string is empty
                else -> {}  // else
            }
            println(e.message)
        }
        return false
    }

    private fun getClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

    fun resolveToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(AUTHORIZATION)

        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_TYPE)) {
            bearerToken.substring(7)
        } else {
            ""
        }
    }
}
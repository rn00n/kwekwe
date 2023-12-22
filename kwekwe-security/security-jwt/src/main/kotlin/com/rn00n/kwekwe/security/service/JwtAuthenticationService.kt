package com.rn00n.kwekwe.security.service

import com.rn00n.kwekwe.security.dto.TokenInfo
import com.rn00n.kwekwe.security.jwt.JwtTokenProvider
import io.jsonwebtoken.JwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class JwtAuthenticationService(
    private val userDetailsService: DefaultUserDetailsService,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
) {

    fun getToken(username: String, password: String): TokenInfo {
        val principalDetails = userDetailsService.loadUserByUsernameAndPassword(username, password)

        val authenticationToken = UsernamePasswordAuthenticationToken(principalDetails, password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createTokenInfo(authentication)
    }

    fun getTokenForOAuthClient(username: String): TokenInfo {
        val principalDetails = userDetailsService.loadUserByUsername(username)

        return jwtTokenProvider.createTokenInfo(principalDetails.username, principalDetails.authorities)
    }

    fun refreshToken(refreshToken: String): TokenInfo {
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw JwtException("invalid refresh token.")
        }

        val authentication = jwtTokenProvider.getAuthentication(refreshToken)
        return jwtTokenProvider.createTokenInfo(authentication)
    }

}
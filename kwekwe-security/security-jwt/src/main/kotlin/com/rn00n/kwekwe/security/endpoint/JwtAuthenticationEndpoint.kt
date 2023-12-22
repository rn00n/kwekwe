package com.rn00n.kwekwe.security.endpoint

import com.rn00n.kwekwe.core.domain.accounts.Account
import com.rn00n.kwekwe.security.dto.*
import com.rn00n.kwekwe.security.service.JwtAuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authentication")
class JwtAuthenticationEndpoint(
    private val jwtAuthenticationService: JwtAuthenticationService,
) {

    /**
     * 인증 토큰 발급
     */
    @PostMapping("/token")
    fun getToken(
        @RequestBody tokenRequest: TokenRequest,
    ): ResponseEntity<TokenInfo> {
        val tokenInfo: TokenInfo = jwtAuthenticationService.getToken(tokenRequest.username, tokenRequest.password)
        return ResponseEntity.ok(tokenInfo)
    }

    /**
     * 인증 토큰 재발급
     */
    @PostMapping("/refresh")
    fun refreshToken(
        @RequestBody tokenRefreshRequest: TokenRefreshRequest,
    ): ResponseEntity<TokenInfo> {
        val tokenInfo: TokenInfo = jwtAuthenticationService.refreshToken(tokenRefreshRequest.refreshToken)
        return ResponseEntity.ok(tokenInfo)
    }

    @GetMapping("/userinfo")
    fun getUserinfo(
        @CurrentAccount account: Account?,
    ): ResponseEntity<AuthenticationUserinfo> {
        val userinfo = account?.toAuthenticationUserinfo() ?: AuthenticationUserinfo.anonymous()
        return ResponseEntity.ok(userinfo)
    }

}

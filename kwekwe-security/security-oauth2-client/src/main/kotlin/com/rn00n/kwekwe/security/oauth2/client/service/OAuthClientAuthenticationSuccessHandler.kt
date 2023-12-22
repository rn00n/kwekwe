package com.rn00n.kwekwe.security.oauth2.client.service

import com.rn00n.kwekwe.security.oauth2.client.dto.OAuth2UserPrincipalDetails
import com.rn00n.kwekwe.security.service.JwtAuthenticationService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

/**
 * restful 방식을 사용할때 사용
 */
@Component
class OAuthClientAuthenticationSuccessHandler(
    private val jwtAuthenticationService: JwtAuthenticationService,
) : SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        clearAuthenticationAttributes(request)

        val principal: OAuth2UserPrincipalDetails = SecurityContextHolder.getContext().authentication.principal as OAuth2UserPrincipalDetails
        val account = principal.oauth2Identity.account

        val createTokenInfo = jwtAuthenticationService.getTokenForOAuthClient(account.username)

        val redirectUrl = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/login/callback")
            .queryParam("tokenType", TokenType.BEARER.value)
            .queryParam("accessToken", createTokenInfo.accessToken)
            .queryParam("refreshToken", createTokenInfo.refreshToken)
            .queryParam("expired", createTokenInfo.expired)
            .build()
            .toUriString()

        response.sendRedirect(redirectUrl)
    }

}

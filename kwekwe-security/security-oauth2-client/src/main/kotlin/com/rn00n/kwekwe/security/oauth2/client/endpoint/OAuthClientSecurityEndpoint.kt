package com.rn00n.kwekwe.security.oauth2.client.endpoint

import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

private val logger = KotlinLogging.logger {}

@Controller
class OAuthClientSecurityEndpoint(
) {

    @GetMapping("/oauth2/login")
    fun login(request: HttpServletRequest): String {
        return "login/login_kakao"
    }

}

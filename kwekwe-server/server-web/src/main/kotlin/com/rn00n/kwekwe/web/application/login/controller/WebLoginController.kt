package com.rn00n.kwekwe.web.application.login.controller

import com.rn00n.kwekwe.core.util.createCookie
import com.rn00n.kwekwe.security.dto.TokenInfo
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/login")
class WebLoginController {

    @GetMapping
    fun login(
        request: HttpServletRequest,
    ): String {
        return "redirect:/oauth2/login"
    }

    @GetMapping("/callback")
    fun loginCallback(
        request: HttpServletRequest,
        response: HttpServletResponse,
        tokenInfo: TokenInfo,
    ): String {
        if (ObjectUtils.isEmpty(tokenInfo)) throw IllegalArgumentException("tokenInfo is bad request")

        response.addCookie(createCookie("tokenType", tokenInfo.tokenType))
        response.addCookie(createCookie("accessToken", tokenInfo.accessToken))
        response.addCookie(createCookie("refreshToken", tokenInfo.refreshToken))
        response.addCookie(createCookie("expired", tokenInfo.expired.toString()))

        return "redirect:/"
    }
}
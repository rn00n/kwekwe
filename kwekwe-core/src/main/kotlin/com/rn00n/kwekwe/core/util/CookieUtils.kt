package com.rn00n.kwekwe.core.util

import jakarta.servlet.http.Cookie

fun createCookie(name: String, value: String): Cookie {
    return Cookie(name, value).apply {
        path = "/"
        isHttpOnly = true
        secure = true // HTTPS를 사용하는 경우에만 true로 설정
        maxAge = 1 * (60 * 60) // 쿠키의 만료 시간 설정 (예: 1시간)
    }
}

package com.rn00n.kwekwe.security.dto

data class TokenInfo(
    val tokenType: String = "Bearer",
    val accessToken: String,
    val refreshToken: String,
    val expired: Long,
)

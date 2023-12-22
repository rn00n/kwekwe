package com.rn00n.kwekwe.security.dto

data class TokenRequest(
    val username: String,
    val password: String,
)
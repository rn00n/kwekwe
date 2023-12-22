package com.rn00n.kwekwe.security.dto

import com.rn00n.kwekwe.core.domain.accounts.Account

data class AuthenticationUserinfo(
    val username: String,
    val name: String,
) {
    companion object {
        fun anonymous(): AuthenticationUserinfo {
            return AuthenticationUserinfo(
                username = "anonymous",
                name = "anonymous",
            )
        }
    }
}

fun Account.toAuthenticationUserinfo(): AuthenticationUserinfo {
    return AuthenticationUserinfo(
        username = this.username,
        name = this.name,
    )
}

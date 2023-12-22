package com.rn00n.kwekwe.core.application.sign.dto

import com.rn00n.kwekwe.core.domain.accounts.Account

data class SignResponse(
    val username: String,
    val name: String,
) {
    companion object
}

fun Account.toSignUpResponse(): SignResponse {
    return SignResponse(
        username = this.username,
        name = this.name,
    )
}

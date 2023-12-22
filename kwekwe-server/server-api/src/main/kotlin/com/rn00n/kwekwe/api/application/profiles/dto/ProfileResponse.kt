package com.rn00n.kwekwe.api.application.profiles.dto

import com.rn00n.kwekwe.core.domain.accounts.Account

data class ProfileResponse(
    val id: Long,
    val username: String,
    val name: String,
)

fun Account.toProfileResponse(): ProfileResponse {
    return ProfileResponse(
        id = this.id,
        username = this.username,
        name = this.name,
    )
}

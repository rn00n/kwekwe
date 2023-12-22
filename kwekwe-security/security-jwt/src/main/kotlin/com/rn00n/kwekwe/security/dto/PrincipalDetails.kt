package com.rn00n.kwekwe.security.dto

import com.rn00n.kwekwe.core.domain.accounts.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

data class PrincipalDetails(
    val account: Account,
) : User(account.username, account.password, account.roles.map { GrantedAuthority { it.name } })

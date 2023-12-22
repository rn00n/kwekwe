package com.rn00n.kwekwe.core.infrastructure.persistence.accounts

import com.rn00n.kwekwe.core.domain.accounts.Account
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AccountQueryDslRepository {
    fun search(search: String, pageable: Pageable): Page<Account>
}
package com.rn00n.kwekwe.core.domain.accounts.service

import com.rn00n.kwekwe.core.domain.accounts.Account
import com.rn00n.kwekwe.core.domain.accounts.repo.AccountRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val repository: AccountRepository,
) {
    fun signUp(account: Account): Account {
        return repository.save(account)
    }

    fun findByUsername(username: String): Account {
        return repository.findByUsername(username) ?: throw RuntimeException("유효하지 않은 username 입니다..")
    }

    fun findById(id: Long): Account {
        return repository.findById(id).orElseThrow { throw RuntimeException("user id is not found") }
    }

    fun search(search: String, pageable: Pageable): Page<Account> {
        return repository.search(search, pageable)
    }
}

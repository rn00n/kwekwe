package com.rn00n.kwekwe.core.domain.accounts.repo

import com.rn00n.kwekwe.core.domain.accounts.Account
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import java.util.*

interface AccountRepository {
    @EntityGraph(attributePaths = ["roles"])
    fun findDetailsByUsername(username: String): Account?

    fun findByUsername(username: String): Account?
    fun save(account: Account): Account
    fun findById(id: Long): Optional<Account>
    fun search(search: String, pageable: Pageable): Page<Account>
}

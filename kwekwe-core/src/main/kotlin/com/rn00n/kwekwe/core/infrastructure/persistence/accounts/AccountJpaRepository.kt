package com.rn00n.kwekwe.core.infrastructure.persistence.accounts

import com.rn00n.kwekwe.core.domain.accounts.Account
import com.rn00n.kwekwe.core.domain.accounts.repo.AccountRepository
import org.springframework.data.jpa.repository.JpaRepository

interface AccountJpaRepository : JpaRepository<Account, Long>, AccountRepository, AccountQueryDslRepository {
}

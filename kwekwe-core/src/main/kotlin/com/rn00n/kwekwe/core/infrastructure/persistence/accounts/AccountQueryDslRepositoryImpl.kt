package com.rn00n.kwekwe.core.infrastructure.persistence.accounts

import com.rn00n.kwekwe.core.commons.querydsl.CustomQuerydslRepositorySupport
import com.rn00n.kwekwe.core.domain.accounts.Account
import com.rn00n.kwekwe.core.domain.accounts.QAccount
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class AccountQueryDslRepositoryImpl : CustomQuerydslRepositorySupport(Account::class.java), AccountQueryDslRepository {

    override fun search(search: String, pageable: Pageable): Page<Account> {
        val account = QAccount.account

        val query = from(account)
        query.where(
            this.containsIgnoreCase(account.username, search),
        )

        val accountJPQLQuery = querydsl!!.applyPagination(pageable, query)
        val fetchResults = accountJPQLQuery.fetchResults()
        return PageImpl(fetchResults.results, pageable, fetchResults.total)
    }

}
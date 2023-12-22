package com.rn00n.kwekwe.api.application.profiles.service

import com.rn00n.kwekwe.api.application.profiles.dto.ProfileResponse
import com.rn00n.kwekwe.api.application.profiles.dto.SearchProfileRequest
import com.rn00n.kwekwe.api.application.profiles.dto.toProfileResponse
import com.rn00n.kwekwe.core.domain.accounts.Account
import com.rn00n.kwekwe.core.domain.accounts.service.AccountService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProfileService(
    private val accountService: AccountService,
) {
    fun get(id: Long): ProfileResponse {
        return accountService.findById(id).toProfileResponse()
    }

    fun search(searchProfileRequest: SearchProfileRequest, pageable: Pageable): Page<ProfileResponse> {
        val accountPage: Page<Account> = accountService.search(searchProfileRequest.search, pageable)
        return accountPage.map {
            it.toProfileResponse()
        }
    }
}
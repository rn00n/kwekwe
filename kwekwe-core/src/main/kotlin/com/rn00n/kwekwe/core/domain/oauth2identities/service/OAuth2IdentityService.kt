package com.rn00n.kwekwe.core.domain.oauth2identities.service

import com.rn00n.kwekwe.core.domain.accounts.Account
import com.rn00n.kwekwe.core.domain.accounts.service.AccountService
import com.rn00n.kwekwe.core.domain.oauth2identities.OAuth2Identity
import com.rn00n.kwekwe.core.domain.oauth2identities.OAuth2Provider
import com.rn00n.kwekwe.core.domain.oauth2identities.repo.OAuth2IdentityRepository
import com.rn00n.kwekwe.core.domain.roles.service.RoleService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OAuth2IdentityService(
    private val accountService: AccountService,
    private val roleService: RoleService,
    private val repository: OAuth2IdentityRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun findDetailsByProviderId(providerId: String): OAuth2Identity {
        return repository.findDetailsByProviderId(providerId) ?: throw RuntimeException("providerId is not found.")
    }

    fun findByProviderId(providerId: String): OAuth2Identity {
        return repository.findByProviderId(providerId) ?: throw RuntimeException("providerId is not found.")
    }

    fun register(registrationId: String, id: String, name: String): OAuth2Identity {
        val username = "${registrationId}_${id}"
        val password = "${registrationId}_${id}_${LocalDateTime.now()}"

        val account = try {
            accountService.findByUsername(username)
        } catch (e: RuntimeException) {
            val userRole = roleService.findById("USER")
            accountService.signUp(
                Account(
                    username = username,
                    password = passwordEncoder.encode(password),
                    name = name,
                    roles = setOf(userRole)
                )
            )
        }

        return OAuth2Identity(
            account = account, provider = OAuth2Provider.valueOf(registrationId.uppercase()), providerId = id
        ).also { repository.save(it) }
    }
}

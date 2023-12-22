package com.rn00n.kwekwe.core.domain.oauth2identities.repo

import com.rn00n.kwekwe.core.domain.oauth2identities.OAuth2Identity
import org.springframework.data.jpa.repository.EntityGraph

interface OAuth2IdentityRepository {
    @EntityGraph(attributePaths = ["account.roles"])
    fun findDetailsByProviderId(providerId: String): OAuth2Identity?

    fun findByProviderId(providerId: String): OAuth2Identity?
    fun save(oAuth2Identity: OAuth2Identity): OAuth2Identity
}

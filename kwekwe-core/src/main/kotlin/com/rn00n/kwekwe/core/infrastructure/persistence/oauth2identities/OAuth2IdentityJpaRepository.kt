package com.rn00n.kwekwe.core.infrastructure.persistence.oauth2identities

import com.rn00n.kwekwe.core.domain.oauth2identities.OAuth2Identity
import com.rn00n.kwekwe.core.domain.oauth2identities.repo.OAuth2IdentityRepository
import org.springframework.data.jpa.repository.JpaRepository

interface OAuth2IdentityJpaRepository : JpaRepository<OAuth2Identity, Long>, OAuth2IdentityRepository {
}

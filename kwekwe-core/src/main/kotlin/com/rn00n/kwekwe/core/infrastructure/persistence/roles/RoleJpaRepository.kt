package com.rn00n.kwekwe.core.infrastructure.persistence.roles

import com.rn00n.kwekwe.core.domain.roles.Role
import com.rn00n.kwekwe.core.domain.roles.repo.RoleRepository
import org.springframework.data.jpa.repository.JpaRepository

interface RoleJpaRepository : JpaRepository<Role, String>, RoleRepository {
}

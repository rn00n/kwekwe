package com.rn00n.kwekwe.core.domain.roles.service

import com.rn00n.kwekwe.core.domain.roles.Role
import com.rn00n.kwekwe.core.domain.roles.repo.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val repository: RoleRepository,
) {
    fun findById(id: String): Role {
        return repository.findById(id).orElseThrow { throw RuntimeException("role id is not found") }
    }

    fun add(role: Role): Role {
        return repository.save(role)
    }
}

package com.rn00n.kwekwe.core.domain.roles.repo

import com.rn00n.kwekwe.core.domain.roles.Role
import java.util.*

interface RoleRepository {
    fun findById(roleId: String): Optional<Role>
    fun save(role: Role): Role
}

package com.rn00n.kwekwe.api.commons.initializer

import com.rn00n.kwekwe.core.domain.accounts.service.AccountService
import com.rn00n.kwekwe.core.domain.roles.Role
import com.rn00n.kwekwe.core.domain.roles.service.RoleService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val roleService: RoleService,
    private val accountService: AccountService,
    @Value("\${init-data}")
    private val initData: Boolean
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        if (initData) {
            initRole("USER", "사용자")
            initRole("ADMIN", "관리자")
        }
    }

    private fun initRole(id: String, name: String): Role {
        return try {
            roleService.findById(id);
        } catch (e: RuntimeException) {
            roleService.add(Role(id, name))
        }
    }
}


package com.rn00n.kwekwe.core.domain.accounts

import com.rn00n.kwekwe.core.domain.roles.Role
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "accounts")
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var username: String,
    var password: String,
    var name: String,
    var createdDateTime: LocalDateTime = LocalDateTime.now(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "account_x_role",
        joinColumns = [JoinColumn(name = "account_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")],
    )
    var roles: Set<Role> = HashSet(),
) {
}

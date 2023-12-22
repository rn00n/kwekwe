package com.rn00n.kwekwe.core.domain.roles

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "roles")
class Role(
    @Id
    var id: String,
    var name: String,
) {
}

package com.rn00n.kwekwe.core.domain.oauth2identities

import com.rn00n.kwekwe.core.domain.accounts.Account
import jakarta.persistence.*

@Entity
@Table(name = "oauth2_identities")
class OAuth2Identity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne
    var account: Account,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var provider: OAuth2Provider,

    @Column(nullable = false, unique = true)
    var providerId: String
) {
}

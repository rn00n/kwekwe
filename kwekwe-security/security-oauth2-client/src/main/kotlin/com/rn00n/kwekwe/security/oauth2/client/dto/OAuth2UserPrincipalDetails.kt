package com.rn00n.kwekwe.security.oauth2.client.dto

import com.rn00n.kwekwe.core.domain.accounts.Account
import com.rn00n.kwekwe.core.domain.oauth2identities.OAuth2Identity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User

data class OAuth2UserPrincipalDetails(
    val oauth2Identity: OAuth2Identity,
    val account: Account = oauth2Identity.account,
    private val _attributes: Map<String, Any>, // DefaultOAuth2User 의 attributes 와 충돌 나기 때문에 private 선언
    val nameAttributeKey: String,
) : DefaultOAuth2User(account.roles.map { GrantedAuthority { it.name } }, _attributes, nameAttributeKey) {
}

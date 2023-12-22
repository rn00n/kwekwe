package com.rn00n.kwekwe.security.oauth2.client.dto

class KakaoOAuth2UserInfo(private val attributes: Map<String, Any>) : OAuth2UserInfo {
    override val id: String
        get() = attributes["id"].toString()
    override val name: String
        get() {
            val properties = attributes["properties"] as Map<*, *>
            return properties["nickname"] as String
        }
    override val registrationId: String
        get() = ""
}

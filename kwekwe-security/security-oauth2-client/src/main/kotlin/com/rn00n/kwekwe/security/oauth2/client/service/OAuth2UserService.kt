package com.rn00n.kwekwe.security.oauth2.client.service

import com.rn00n.kwekwe.core.domain.oauth2identities.OAuth2Identity
import com.rn00n.kwekwe.core.domain.oauth2identities.service.OAuth2IdentityService
import com.rn00n.kwekwe.security.oauth2.client.dto.KakaoOAuth2UserInfo
import com.rn00n.kwekwe.security.oauth2.client.dto.OAuth2UserInfo
import com.rn00n.kwekwe.security.oauth2.client.dto.OAuth2UserPrincipalDetails
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2UserService(
    private val oAuth2IdentityService: OAuth2IdentityService,
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User: OAuth2User = super.loadUser(userRequest)

        val userNameAttributeName: String = userRequest.clientRegistration
            .providerDetails
            .userInfoEndpoint
            .userNameAttributeName

        val userInfo: OAuth2UserInfo = when (userRequest.clientRegistration.registrationId) {
            "kakao" -> KakaoOAuth2UserInfo(oAuth2User.attributes)
            else -> throw IllegalArgumentException("지원하지 않는 Client 입니다.")
        }

        val oauth2Identity: OAuth2Identity = try {
            oAuth2IdentityService.findDetailsByProviderId(userInfo.id)
        } catch (e: RuntimeException) {
            oAuth2IdentityService.register(userRequest.clientRegistration.registrationId, userInfo.id, userInfo.name)
        }

        val attributes = HashMap(oAuth2User.attributes)
        attributes["username"] = oauth2Identity.account.username

        return OAuth2UserPrincipalDetails(
            oauth2Identity = oauth2Identity,
            _attributes = oAuth2User.attributes,
            nameAttributeKey = userNameAttributeName
        )
    }
}

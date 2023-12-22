package com.rn00n.kwekwe.security.oauth2.client.config

import com.rn00n.kwekwe.security.oauth2.client.service.OAuth2UserService
import com.rn00n.kwekwe.security.oauth2.client.service.OAuthClientAuthenticationSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher

@Configuration
@EnableMethodSecurity
class OAuth2ClientSecurityConfig(
    private val userDetailsService: OAuth2UserService,
    private val authenticationSuccessHandler: OAuthClientAuthenticationSuccessHandler,
) {

    @Bean
    @Order(value = 1)
    fun manittoOAuth2ClientSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatchers { // root 패턴
                it.requestMatchers(
                    antMatcher("/oauth2/**"),
                    antMatcher("/login/oauth2/code/**")
                )
            }
            .authorizeHttpRequests { // root 에 포함된 세부 패턴
                it
                    .requestMatchers(
                        antMatcher("/**/img/**"),
                        antMatcher("/**/css/**"),
                        antMatcher("/**/js/**"),
                    ).permitAll()
                    .requestMatchers(antMatcher("/oauth2/login")).permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login {
                it
                    .loginPage("/oauth2/login")
                    .successHandler(authenticationSuccessHandler) // web mvc 방식으로 사용하기 위해 제거
                    .userInfoEndpoint { endpoint ->
                        endpoint.userService(userDetailsService)
                    }
            }
        return http.build()
    }

}

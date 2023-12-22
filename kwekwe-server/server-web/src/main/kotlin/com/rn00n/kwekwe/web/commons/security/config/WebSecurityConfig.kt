package com.rn00n.kwekwe.web.commons.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher
import org.springframework.web.servlet.handler.HandlerMappingIntrospector

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    @Order(value = 20)
    fun defaultRouteSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .securityMatchers { // root 패턴
                it.requestMatchers(
                    MvcRequestMatcher(HandlerMappingIntrospector(), "/**"),
                )
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        antMatcher("/**/img/**"),
                        antMatcher("/**/css/**"),
                        antMatcher("/**/js/**"),
                    ).permitAll()
                    .requestMatchers(
                        MvcRequestMatcher(HandlerMappingIntrospector(), "/"),
                        MvcRequestMatcher(HandlerMappingIntrospector(), "/login"),
                        MvcRequestMatcher(HandlerMappingIntrospector(), "/error"),
                    ).permitAll()
                    .anyRequest().authenticated()
            }
        return http.build()
    }

}
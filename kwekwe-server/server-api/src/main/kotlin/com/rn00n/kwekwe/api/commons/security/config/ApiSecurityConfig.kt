package com.rn00n.kwekwe.api.commons.security.config

import com.rn00n.kwekwe.security.jwt.JwtAuthenticationFilter
import com.rn00n.kwekwe.security.jwt.JwtTokenProvider
import com.rn00n.kwekwe.security.service.DefaultUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.servlet.handler.HandlerMappingIntrospector

@Configuration
@EnableWebSecurity
class ApiSecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: DefaultUserDetailsService,
) {

    /**
     * API Filter
     */
    @Bean
    @Order(value = 1)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .securityMatchers { // root 패턴
                it.requestMatchers(
                    AntPathRequestMatcher.antMatcher("/api/**")
                )
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers(MvcRequestMatcher(HandlerMappingIntrospector(), "/api/signup")).permitAll()
                    .requestMatchers(MvcRequestMatcher(HandlerMappingIntrospector(), "/authentication/**")).permitAll()
                    .requestMatchers(MvcRequestMatcher(HandlerMappingIntrospector(), "/api/profiles/**")).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .userDetailsService(userDetailsService)
        return http.build()
    }

}

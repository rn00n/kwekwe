package com.rn00n.kwekwe.security.config

import com.rn00n.kwekwe.security.filter.StoreRequestUrlInSessionFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig {

    @Bean
    fun loggingFilter(): FilterRegistrationBean<StoreRequestUrlInSessionFilter> {
        val registrationBean = FilterRegistrationBean<StoreRequestUrlInSessionFilter>()

        registrationBean.filter = StoreRequestUrlInSessionFilter()
        registrationBean.addUrlPatterns("/*")  // Apply for all requests

        return registrationBean
    }

}

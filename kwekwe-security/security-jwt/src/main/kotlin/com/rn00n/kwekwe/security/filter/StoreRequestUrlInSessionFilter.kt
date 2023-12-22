package com.rn00n.kwekwe.security.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest

class StoreRequestUrlInSessionFilter : Filter {

    companion object {
        const val LAST_REQUEST_URL_SESSION_KEY = "lastRequestedUrl"
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        if (request is HttpServletRequest) {
            val requestURL = request.requestURL

            if (!requestURL.contains("/login")
            ) {
                val session = request.session
                session.setAttribute(LAST_REQUEST_URL_SESSION_KEY, request.requestURL)
            }
        }
        chain?.doFilter(request, response)
    }
}
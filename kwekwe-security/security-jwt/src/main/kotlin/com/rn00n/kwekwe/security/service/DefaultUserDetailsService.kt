package com.rn00n.kwekwe.security.service

import com.rn00n.kwekwe.core.domain.accounts.Account
import com.rn00n.kwekwe.core.domain.accounts.repo.AccountRepository
import com.rn00n.kwekwe.security.dto.PrincipalDetails
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class DefaultUserDetailsService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {

    fun loadUserByUsernameAndPassword(username: String, rawPassword: String): UserDetails =
        accountRepository.findDetailsByUsername(username)
            ?.let {
                if (passwordEncoder.matches(rawPassword, it.password)) {
                    createUserDetails(it)
                } else {
                    throw BadCredentialsException("비밀번호가 틀립니다.")
                }
            } ?: throw UsernameNotFoundException("해당 유저는 없습니다.")

    override fun loadUserByUsername(username: String): UserDetails =
        accountRepository.findDetailsByUsername(username)
            ?.let { createUserDetails(it) } ?: throw UsernameNotFoundException("해당 유저는 없습니다.")

    private fun createUserDetails(account: Account): UserDetails {
        return PrincipalDetails(account)
    }
}

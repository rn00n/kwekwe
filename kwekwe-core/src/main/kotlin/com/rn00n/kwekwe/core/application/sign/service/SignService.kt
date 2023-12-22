package com.rn00n.kwekwe.core.application.sign.service

import com.rn00n.kwekwe.core.application.sign.dto.SignResponse
import com.rn00n.kwekwe.core.application.sign.dto.SignUpRequest
import com.rn00n.kwekwe.core.application.sign.dto.toSignUpResponse
import com.rn00n.kwekwe.core.domain.accounts.Account
import com.rn00n.kwekwe.core.domain.accounts.service.AccountService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignService(
    private val accountService: AccountService,
    private val passwordEncoder: PasswordEncoder,
) {

    fun signUp(signUpRequest: SignUpRequest): SignResponse {
        var account = Account(
            username = signUpRequest.username,
            password = passwordEncoder.encode(signUpRequest.password),
            name = signUpRequest.name,
        )
        account = accountService.signUp(account);
        return account.toSignUpResponse()
    }

}

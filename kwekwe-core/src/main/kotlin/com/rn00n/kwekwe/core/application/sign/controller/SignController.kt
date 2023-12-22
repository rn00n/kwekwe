package com.rn00n.kwekwe.core.application.sign.controller

import com.rn00n.kwekwe.core.application.sign.dto.SignResponse
import com.rn00n.kwekwe.core.application.sign.dto.SignUpRequest
import com.rn00n.kwekwe.core.application.sign.service.SignService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sign")
class SignController(
    private val signService: SignService,
) {

    @PostMapping("/up")
    fun signUp(
        @RequestBody signUpRequest: SignUpRequest,
    ): ResponseEntity<SignResponse> {
        val signResponse: SignResponse = signService.signUp(signUpRequest);
        return ResponseEntity.ok(signResponse)
    }
}

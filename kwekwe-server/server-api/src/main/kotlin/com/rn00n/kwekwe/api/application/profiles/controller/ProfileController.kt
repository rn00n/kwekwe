package com.rn00n.kwekwe.api.application.profiles.controller

import com.rn00n.kwekwe.api.application.profiles.dto.ProfileResponse
import com.rn00n.kwekwe.api.application.profiles.dto.SearchProfileRequest
import com.rn00n.kwekwe.api.application.profiles.service.ProfileService
import com.rn00n.kwekwe.core.commons.base.BasePage
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profiles")
class ProfileController(
    private val profileService: ProfileService,
) {

    @GetMapping("/{id}")
    fun getProfile(
        @PathVariable id: Long
    ): ResponseEntity<ProfileResponse> {
        val profile = profileService.get(id)
        return ResponseEntity.ok(profile)
    }

    @GetMapping
    fun searchProfiles(
        searchProfileRequest: SearchProfileRequest,
        @PageableDefault(size = 5) pageable: Pageable,
    ): ResponseEntity<BasePage<ProfileResponse>> {
        val profilePage: Page<ProfileResponse> = profileService.search(searchProfileRequest, pageable)
        return ResponseEntity.ok(BasePage.of(profilePage))
    }

}
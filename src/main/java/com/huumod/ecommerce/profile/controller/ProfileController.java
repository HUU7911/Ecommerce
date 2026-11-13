package com.huumod.ecommerce.profile.controller;

import com.huumod.ecommerce.common.ApiResponse;
import com.huumod.ecommerce.profile.dto.request.ProfileCreationRequest;
import com.huumod.ecommerce.profile.dto.response.ProfileResponse;
import com.huumod.ecommerce.profile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileController {

    ProfileService profileService;

    @PostMapping("/create")
    ApiResponse<ProfileResponse> createProfile(@RequestBody @Valid ProfileCreationRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .results(profileService.createProfile(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ProfileResponse>> getAll() {
        return ApiResponse.<List<ProfileResponse>>builder()
                .results(profileService.getAll())
                .build();
    }

    @GetMapping("/my-profile")
    ApiResponse<ProfileResponse> getMyProfile() {
        return ApiResponse.<ProfileResponse>builder()
                .results(profileService.getMyProfile())
                .build();
    }

    @DeleteMapping("/delete")
    ApiResponse<Void> deleteProfile() {
        profileService.deleteProfile();
        return ApiResponse.<Void>builder()
                .message("profile has been deleted")
                .build();
    }
}

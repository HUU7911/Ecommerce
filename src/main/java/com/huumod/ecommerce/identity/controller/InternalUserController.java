package com.huumod.ecommerce.identity.controller;

import com.huumod.ecommerce.common.ApiResponse;
import com.huumod.ecommerce.identity.dto.request.UserCreationRequest;
import com.huumod.ecommerce.identity.dto.response.UserResponse;
import com.huumod.ecommerce.identity.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalUserController {

    UserService userService;

    @PostMapping("/create")
    ApiResponse<UserResponse> internalCreateUser(UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .results(userService.internalCreateUser(request))
                .build();
    }
}

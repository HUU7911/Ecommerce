package com.huumod.ecommerce.identity.controller;

import com.huumod.ecommerce.common.ApiResponse;
import com.huumod.ecommerce.identity.dto.request.UserCreationRequest;
import com.huumod.ecommerce.identity.dto.request.UserUpdateRequest;
import com.huumod.ecommerce.identity.dto.response.UserResponse;
import com.huumod.ecommerce.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/create")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .results(userService.createUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .results(userService.getAllUsers())
                .build();
    }

    @GetMapping("/my-profile")
    ApiResponse<UserResponse> getMyProfile() {
        return ApiResponse.<UserResponse>builder()
                .results(userService.getMyInfor())
                .build();
    }

    @PutMapping("/update")
    ApiResponse<UserResponse> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .results(userService.updateUser(request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    ApiResponse<Void> deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return ApiResponse.<Void>builder()
                .message("User has been deleted")
                .build();
    }
}

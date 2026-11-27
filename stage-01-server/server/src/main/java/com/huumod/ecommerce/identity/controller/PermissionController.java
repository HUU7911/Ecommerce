package com.huumod.ecommerce.identity.controller;

import com.huumod.ecommerce.common.ApiResponse;
import com.huumod.ecommerce.identity.dto.request.PermissionRequest;
import com.huumod.ecommerce.identity.dto.response.PermissionResponse;
import com.huumod.ecommerce.identity.service.PermissionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @PostMapping("/create")
    ApiResponse<PermissionResponse> createPermission(@RequestBody @Valid PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .results(permissionService.createPermission(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .results(permissionService.findAll())
                .build();
    }
}

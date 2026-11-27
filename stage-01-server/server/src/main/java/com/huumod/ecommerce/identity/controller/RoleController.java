package com.huumod.ecommerce.identity.controller;

import com.huumod.ecommerce.common.ApiResponse;
import com.huumod.ecommerce.identity.dto.request.RoleRequest;
import com.huumod.ecommerce.identity.dto.request.RoleUpdateRequest;
import com.huumod.ecommerce.identity.dto.response.RoleResponse;
import com.huumod.ecommerce.identity.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @PostMapping("/create")
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder()
                .results(roleService.createRole(roleRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> findAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .results(roleService.findAll())
                .build();
    }

    @PutMapping("/update/{name}")
    ApiResponse<RoleResponse> updateRole(@PathVariable String name, @RequestBody RoleUpdateRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .results(roleService.updateRole(name, request))
                .build();
    }
}

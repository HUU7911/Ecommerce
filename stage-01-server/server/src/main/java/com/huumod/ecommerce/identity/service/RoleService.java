package com.huumod.ecommerce.identity.service;

import com.huumod.ecommerce.common.exception.AppException;
import com.huumod.ecommerce.common.exception.ErrorCode;
import com.huumod.ecommerce.identity.dto.request.RoleRequest;
import com.huumod.ecommerce.identity.dto.request.RoleUpdateRequest;
import com.huumod.ecommerce.identity.dto.response.RoleResponse;
import com.huumod.ecommerce.identity.entity.Permission;
import com.huumod.ecommerce.identity.mapper.RoleMapper;
import com.huumod.ecommerce.identity.repository.PermissionRepository;
import com.huumod.ecommerce.identity.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleMapper roleMapper;
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var role = roleMapper.toRole(request);
        Set<Permission> permissions = new HashSet<>(
                permissionRepository.findAllById(request.getPermissions()));
        role.setPermissions(permissions);

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse updateRole(String name, RoleUpdateRequest request) {
        var role = roleRepository.findById(name).orElseThrow(
                () -> new AppException(ErrorCode.UNAUTHENTICATED)
        );

        roleMapper.updateRole(role, request);
        Set<Permission> permissions = new HashSet<>(
                permissionRepository.findAllById(request.getPermissions()));
        role.setPermissions(permissions);

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleResponse> findAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }
}

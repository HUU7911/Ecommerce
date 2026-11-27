package com.huumod.ecommerce.identity.service;

import com.huumod.ecommerce.common.exception.AppException;
import com.huumod.ecommerce.common.exception.ErrorCode;
import com.huumod.ecommerce.identity.dto.request.PermissionRequest;
import com.huumod.ecommerce.identity.dto.response.PermissionResponse;
import com.huumod.ecommerce.identity.mapper.PermissionMapper;
import com.huumod.ecommerce.identity.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionMapper permissionMapper;
    PermissionRepository permissionRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse createPermission(PermissionRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var permission = permissionMapper.toPermission(request);

        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> findAll() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }
}

package com.huumod.ecommerce.identity.mapper;

import com.huumod.ecommerce.identity.dto.request.PermissionRequest;
import com.huumod.ecommerce.identity.dto.response.PermissionResponse;
import com.huumod.ecommerce.identity.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}

package com.huumod.ecommerce.identity.mapper;

import com.huumod.ecommerce.identity.dto.request.RoleRequest;
import com.huumod.ecommerce.identity.dto.request.RoleUpdateRequest;
import com.huumod.ecommerce.identity.dto.response.RoleResponse;
import com.huumod.ecommerce.identity.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissions", ignore = true)
    void updateRole(@MappingTarget Role role, RoleUpdateRequest request);
}

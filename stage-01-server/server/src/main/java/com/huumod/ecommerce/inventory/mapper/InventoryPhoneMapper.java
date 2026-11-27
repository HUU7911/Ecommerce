package com.huumod.ecommerce.inventory.mapper;

import com.huumod.ecommerce.inventory.dto.request.InventoryPhoneRequest;
import com.huumod.ecommerce.inventory.dto.request.InventoryPhoneUpdateRequest;
import com.huumod.ecommerce.inventory.dto.response.InventoryPhoneResponse;
import com.huumod.ecommerce.inventory.entity.InventoryPhone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryPhoneMapper {

    @Mapping(target = "option", ignore = true)
    InventoryPhone toInventoryPhone(InventoryPhoneRequest request);

    @Mapping(target = "option", ignore = true)
    InventoryPhoneResponse toInventoryPhoneResponse(InventoryPhone phone);

    @Mapping(target = "option", ignore = true)
    void updateInventoryPhone(@MappingTarget InventoryPhone inventoryPhone, InventoryPhoneUpdateRequest request);
}

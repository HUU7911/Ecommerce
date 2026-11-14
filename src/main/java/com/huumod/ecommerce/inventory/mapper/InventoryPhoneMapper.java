package com.huumod.ecommerce.inventory.mapper;

import com.huumod.ecommerce.inventory.dto.request.InventoryPhoneRequest;
import com.huumod.ecommerce.inventory.dto.request.InventoryPhoneUpdateRequest;
import com.huumod.ecommerce.inventory.dto.response.InventoryPhoneResponse;
import com.huumod.ecommerce.inventory.entity.InventoryPhone;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryPhoneMapper {
    InventoryPhone toInventoryPhone(InventoryPhoneRequest request);
    InventoryPhoneResponse toInventoryPhoneResponse(InventoryPhone phone);
    void updateInventoryPhone(@MappingTarget InventoryPhone inventoryPhone, InventoryPhoneUpdateRequest request);
}

package com.huumod.ecommerce.inventory.mapper;

import com.huumod.ecommerce.inventory.dto.request.InventoryAccessoryRequest;
import com.huumod.ecommerce.inventory.dto.response.InventoryAccessoryResponse;
import com.huumod.ecommerce.inventory.entity.InventoryAccessory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryAccessoryMapper {
    InventoryAccessory toInventoryAccessory(InventoryAccessoryRequest request);
    InventoryAccessoryResponse toInventoryAccessoryResponse(InventoryAccessory accessory);
}

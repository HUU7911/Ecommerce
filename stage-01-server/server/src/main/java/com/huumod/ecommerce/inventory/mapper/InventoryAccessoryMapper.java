package com.huumod.ecommerce.inventory.mapper;

import com.huumod.ecommerce.inventory.dto.response.InventoryAccessoryResponse;
import com.huumod.ecommerce.inventory.entity.InventoryAccessory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryAccessoryMapper {

    @Mapping(target = "availableQuantity", expression = "java(entity.getAvailableQuantity())")
    @Mapping(target = "lowStock", expression = "java(entity.getAvailableQuantity() <= entity.getLowStockThreshold())")
    InventoryAccessoryResponse toResponse(InventoryAccessory entity);
}
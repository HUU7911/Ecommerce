package com.huumod.ecommerce.inventory.mapper;

import com.huumod.ecommerce.inventory.dto.response.InventoryPhoneResponse;
import com.huumod.ecommerce.inventory.entity.InventoryPhone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryPhoneMapper {

    @Mapping(target = "availableQuantity", expression = "java(entity.getAvailableQuantity())")
    @Mapping(target = "lowStock", expression = "java(entity.getAvailableQuantity() <= entity.getLowStockThreshold())")
    InventoryPhoneResponse toResponse(InventoryPhone entity);
}
package com.huumod.ecommerce.product.mapper;

import com.huumod.ecommerce.product.dto.request.AccessoryRequest;
import com.huumod.ecommerce.product.dto.request.AccessoryUpdateRequest;
import com.huumod.ecommerce.product.dto.response.AccessoryResponse;
import com.huumod.ecommerce.product.entity.Accessory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccessoryMapper {
    @Mapping(target = "categories", ignore = true)
    Accessory toAccessory(AccessoryRequest request);

    @Mapping(target = "categories", ignore = true)
    AccessoryResponse toAccessoryResponse(Accessory accessory);

    @Mapping(target = "categories", ignore = true)
    void updateAccessory(@MappingTarget Accessory accessory, AccessoryUpdateRequest request);
}

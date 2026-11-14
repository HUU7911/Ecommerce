package com.huumod.ecommerce.product.mapper;

import com.huumod.ecommerce.product.dto.request.PhoneRequest;
import com.huumod.ecommerce.product.dto.request.PhoneUpdateRequest;
import com.huumod.ecommerce.product.dto.response.PhoneResponse;
import com.huumod.ecommerce.product.entity.Phone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    @Mapping(target = "detail", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Phone toPhone(PhoneRequest request);

    @Mapping(target = "detail", ignore = true)
    @Mapping(target = "categories", ignore = true)
    PhoneResponse toPhoneResponse(Phone phone);

    @Mapping(target = "detail", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updatePhone(@MappingTarget Phone phone, PhoneUpdateRequest request);
}

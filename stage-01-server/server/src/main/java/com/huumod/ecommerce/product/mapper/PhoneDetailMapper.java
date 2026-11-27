package com.huumod.ecommerce.product.mapper;

import com.huumod.ecommerce.product.dto.request.PhoneDetailRequest;
import com.huumod.ecommerce.product.dto.request.PhoneDetailUpdateRequest;
import com.huumod.ecommerce.product.dto.response.PhoneDetailResponse;
import com.huumod.ecommerce.product.entity.PhoneDetail;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PhoneDetailMapper {
    PhoneDetail toPhoneDetail(PhoneDetailRequest request);
    PhoneDetailResponse toPhoneDetailResponse(PhoneDetail phoneDetail);
    void updatePhoneDetail(@MappingTarget PhoneDetail detail, PhoneDetailUpdateRequest request);
}

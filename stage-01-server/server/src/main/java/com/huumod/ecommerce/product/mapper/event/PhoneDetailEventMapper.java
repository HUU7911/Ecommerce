package com.huumod.ecommerce.product.mapper.event;

import com.huumod.ecommerce.contract.event.PhoneEventDetail;
import com.huumod.ecommerce.product.entity.PhoneDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneDetailEventMapper {
    PhoneEventDetail toPhoneEventDetail(PhoneDetail detail);
}

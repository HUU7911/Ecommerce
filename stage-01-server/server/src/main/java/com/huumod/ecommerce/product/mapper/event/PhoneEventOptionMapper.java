package com.huumod.ecommerce.product.mapper.event;

import com.huumod.ecommerce.contract.event.PhoneEventOption;
import com.huumod.ecommerce.product.entity.PhoneOption;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneEventOptionMapper {
    PhoneEventOption toPhoneEventOption(PhoneOption option);
}

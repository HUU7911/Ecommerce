package com.huumod.ecommerce.search.mapper;

import com.huumod.ecommerce.contract.event.PhoneEventDetail;
import com.huumod.ecommerce.search.document.phone.PhoneDocumentDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneDetailDocumentMapper {
    PhoneDocumentDetail toPhoneDocumentDetail(PhoneEventDetail detail);
}

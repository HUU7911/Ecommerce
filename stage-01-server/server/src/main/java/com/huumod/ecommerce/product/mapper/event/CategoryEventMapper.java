package com.huumod.ecommerce.product.mapper.event;

import com.huumod.ecommerce.contract.event.CategoryEvent;
import com.huumod.ecommerce.product.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryEventMapper {
    CategoryEvent toCategoryEvent(Category category);
}

package com.huumod.ecommerce.product.mapper;

import com.huumod.ecommerce.product.dto.request.CategoryRequest;
import com.huumod.ecommerce.product.dto.response.CategoryResponse;
import com.huumod.ecommerce.product.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);
    CategoryResponse toCategoryResponse(Category category);
}

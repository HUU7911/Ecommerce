package com.huumod.ecommerce.product.service;

import com.huumod.ecommerce.common.exception.AppException;
import com.huumod.ecommerce.common.exception.ErrorCode;
import com.huumod.ecommerce.product.dto.request.CategoryRequest;
import com.huumod.ecommerce.product.dto.response.CategoryResponse;
import com.huumod.ecommerce.product.mapper.CategoryMapper;
import com.huumod.ecommerce.product.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    CategoryMapper categoryMapper;
    CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsById(request.getName()))
            throw new AppException(ErrorCode.UNAUTHORIZED);

        var category = categoryMapper.toCategory(request);

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    public void deleteCategory(String name) {
        categoryRepository.deleteById(name);
    }
}

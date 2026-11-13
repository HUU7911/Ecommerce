package com.huumod.ecommerce.product.controller;

import com.huumod.ecommerce.common.ApiResponse;
import com.huumod.ecommerce.product.dto.request.CategoryRequest;
import com.huumod.ecommerce.product.dto.response.CategoryResponse;
import com.huumod.ecommerce.product.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;

    @PostMapping("/create")
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .results(categoryService.createCategory(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<CategoryResponse>> getAll() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .results(categoryService.getAll())
                .build();
    }

    @DeleteMapping("/delete/{name}")
    ApiResponse<Void> deleteCategory(@PathVariable("name") String name) {
        categoryService.deleteCategory(name);
        return ApiResponse.<Void>builder()
                .message("category has been deleted")
                .build();
    }
}

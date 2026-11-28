package com.huumod.ecommerce.search.controller;

import com.huumod.ecommerce.contract.common.ApiResponse;
import com.huumod.ecommerce.contract.common.PageResponse;
import com.huumod.ecommerce.search.document.phone.PhoneDocument;
import com.huumod.ecommerce.search.service.SearchService;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchController {

    SearchService searchService;

    @GetMapping("/product")
    ApiResponse<PageResponse<PhoneDocument>> search(
            @RequestParam(value = "keyword", required = false) @Size(max = 100) String keyword,
            @RequestParam(value = "category", required = false) @Size(max = 100) String category,
            @RequestParam(value = "price", required = false) @PositiveOrZero BigDecimal price,
            @RequestParam(value = "page",required = false, defaultValue = "10") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
            ) {
        return ApiResponse.<PageResponse<PhoneDocument>>builder()
                .results(searchService.searchProduct(keyword, category, price, page, size))
                .build();
    }
}

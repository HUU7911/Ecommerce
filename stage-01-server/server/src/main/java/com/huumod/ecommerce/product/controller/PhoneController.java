package com.huumod.ecommerce.product.controller;

import com.huumod.ecommerce.common.ApiResponse;
import com.huumod.ecommerce.common.PageResponse;
import com.huumod.ecommerce.product.dto.request.PhoneRequest;
import com.huumod.ecommerce.product.dto.request.PhoneUpdateRequest;
import com.huumod.ecommerce.product.dto.response.PhoneResponse;
import com.huumod.ecommerce.product.service.PhoneService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/phone")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhoneController {

    PhoneService phoneService;

    @PostMapping("/create")
    ApiResponse<PhoneResponse> createPhone(@RequestPart PhoneRequest request, @RequestPart MultipartFile file)
            throws Exception {
        return ApiResponse.<PhoneResponse>builder()
                .results(phoneService.createPhone(request, file))
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<PhoneResponse>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "8") int page,
            @RequestParam(value = "size", required = false, defaultValue = "8") int size
    ) {
        return ApiResponse.<PageResponse<PhoneResponse>>builder()
                .results(phoneService.getAll(page, size))
                .build();
    }

    @PutMapping("/update/{id}")
    ApiResponse<PhoneResponse> updatePhone(@PathVariable("id") String id, @RequestPart PhoneUpdateRequest request, @RequestPart MultipartFile file)
            throws Exception {
        return ApiResponse.<PhoneResponse>builder()
                .results(phoneService.updatePhone(id, request, file))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    ApiResponse<Void> deletePhone(@PathVariable("id") String id) {
        phoneService.deletePhone(id);
        return ApiResponse.<Void>builder()
                .message("phone has been deleted")
                .build();
    }
}

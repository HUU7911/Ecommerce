package com.huumod.ecommerce.product.controller;

import com.huumod.ecommerce.common.ApiResponse;
import com.huumod.ecommerce.product.dto.request.PhoneDetailRequest;
import com.huumod.ecommerce.product.dto.request.PhoneDetailUpdateRequest;
import com.huumod.ecommerce.product.dto.response.PhoneDetailResponse;
import com.huumod.ecommerce.product.service.PhoneDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/detail")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhoneDetailController {

    PhoneDetailService phoneDetailService;

    @PostMapping("/create")
    ApiResponse<PhoneDetailResponse> createPhoneDetail(@RequestBody PhoneDetailRequest request) {
        return ApiResponse.<PhoneDetailResponse>builder()
                .results(phoneDetailService.createPhoneDetail(request))
                .build();
    }

    @PutMapping("/update/{id}")
    ApiResponse<PhoneDetailResponse> updatePhoneDetail(@PathVariable("id") Long id, @RequestBody PhoneDetailUpdateRequest request) {
        return ApiResponse.<PhoneDetailResponse>builder()
                .results(phoneDetailService.updatePhoneDetail(id, request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    ApiResponse<Void> deletePhoneDetail(@PathVariable("id") Long id){
        phoneDetailService.deleteDetail(id);
        return ApiResponse.<Void>builder()
                .message("phone detail has been deleted")
                .build();
    }
}

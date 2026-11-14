package com.huumod.ecommerce.product.controller;

import com.huumod.ecommerce.common.ApiResponse;
import com.huumod.ecommerce.product.dto.request.PhoneRequest;
import com.huumod.ecommerce.product.dto.request.PhoneUpdateRequest;
import com.huumod.ecommerce.product.dto.response.PhoneResponse;
import com.huumod.ecommerce.product.service.PhoneService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phone")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhoneController {

    PhoneService phoneService;

    @PostMapping("/create")
    ApiResponse<PhoneResponse> createPhone(@RequestBody PhoneRequest request) {
        return ApiResponse.<PhoneResponse>builder()
                .results(phoneService.createPhone(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PhoneResponse>> getAll() {
        return ApiResponse.<List<PhoneResponse>>builder()
                .results(phoneService.getAll())
                .build();
    }

    @PutMapping("/update/{id}")
    ApiResponse<PhoneResponse> updatePhone(@PathVariable("id") String id, @RequestBody PhoneUpdateRequest request) {
        return ApiResponse.<PhoneResponse>builder()
                .results(phoneService.updatePhone(id, request))
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

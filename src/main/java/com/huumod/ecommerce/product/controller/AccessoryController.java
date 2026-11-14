package com.huumod.ecommerce.product.controller;

import com.huumod.ecommerce.common.ApiResponse;
import com.huumod.ecommerce.product.dto.request.AccessoryRequest;
import com.huumod.ecommerce.product.dto.request.AccessoryUpdateRequest;
import com.huumod.ecommerce.product.dto.response.AccessoryResponse;
import com.huumod.ecommerce.product.service.AccessoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accessory")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccessoryController {

    AccessoryService accessoryService;

    @PostMapping("/create")
    ApiResponse<AccessoryResponse> createAccessory(@RequestBody AccessoryRequest request) {
        return ApiResponse.<AccessoryResponse>builder()
                .results(accessoryService.createAccessory(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<AccessoryResponse>> getAll() {
        return ApiResponse.<List<AccessoryResponse>>builder()
                .results(accessoryService.getAll())
                .build();
    }

    @PutMapping("/update/{id}")
    ApiResponse<AccessoryResponse> updateAccessory(@PathVariable("id") String id, @RequestBody AccessoryUpdateRequest request) {
        return ApiResponse.<AccessoryResponse>builder()
                .results(accessoryService.updateCategory(id, request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    ApiResponse<Void> deleteById(@PathVariable("id") String id) {
        accessoryService.deleteAccessory(id);
        return ApiResponse.<Void>builder()
                .message("accessory has been deleted")
                .build();
    }
}

package com.huumod.ecommerce.product.controller;

import com.huumod.ecommerce.common.ApiResponse;
import com.huumod.ecommerce.common.PageResponse;
import com.huumod.ecommerce.product.dto.request.AccessoryRequest;
import com.huumod.ecommerce.product.dto.request.AccessoryUpdateRequest;
import com.huumod.ecommerce.product.dto.response.AccessoryResponse;
import com.huumod.ecommerce.product.service.AccessoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/accessory")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccessoryController {

    AccessoryService accessoryService;

    @PostMapping("/create")
    ApiResponse<AccessoryResponse> createAccessory(@RequestPart AccessoryRequest request, @RequestPart MultipartFile file)
            throws Exception {
        return ApiResponse.<AccessoryResponse>builder()
                .results(accessoryService.createAccessory(request, file))
                .build();
    }

    @GetMapping
    ApiResponse<PageResponse<AccessoryResponse>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "8") int page,
            @RequestParam(value = "page", required = false, defaultValue = "8") int size
    ) {
        return ApiResponse.<PageResponse<AccessoryResponse>>builder()
                .results(accessoryService.getAll(page, size))
                .build();
    }

    @PutMapping("/update/{id}")
    ApiResponse<AccessoryResponse> updateAccessory(@PathVariable("id") String id, @RequestPart AccessoryUpdateRequest request, @RequestPart MultipartFile file)
            throws Exception {
        return ApiResponse.<AccessoryResponse>builder()
                .results(accessoryService.updateAccessory(id, request, file))
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

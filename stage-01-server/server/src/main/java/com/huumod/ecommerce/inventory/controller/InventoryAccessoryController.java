package com.huumod.ecommerce.inventory.controller;

import com.huumod.ecommerce.contract.ApiResponse;
import com.huumod.ecommerce.contract.PageResponse;
import com.huumod.ecommerce.contract.constant.InventoryStatus;
import com.huumod.ecommerce.inventory.dto.request.*;
import com.huumod.ecommerce.inventory.dto.response.InventoryAccessoryResponse;
import com.huumod.ecommerce.inventory.service.InventoryAccessoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventories/accessories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryAccessoryController {

    InventoryAccessoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<InventoryAccessoryResponse> create(
            @Valid @RequestBody InventoryAccessoryRequest request) {

        return ApiResponse.<InventoryAccessoryResponse>builder()
                .message("Accessory inventory created successfully")
                .results(service.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<InventoryAccessoryResponse>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) InventoryStatus status) {

        return ApiResponse.<PageResponse<InventoryAccessoryResponse>>builder()
                .message("Get accessory inventories successfully")
                .results(service.getAll(page, size, status))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<InventoryAccessoryResponse> getById(
            @PathVariable String id) {

        return ApiResponse.<InventoryAccessoryResponse>builder()
                .message("Get accessory inventory successfully")
                .results(service.getById(id))
                .build();
    }

    @GetMapping("/products/{productId}")
    public ApiResponse<InventoryAccessoryResponse> getByProductId(
            @PathVariable String productId) {

        return ApiResponse.<InventoryAccessoryResponse>builder()
                .message("Get accessory inventory successfully")
                .results(service.getByProductId(productId))
                .build();
    }

    @PostMapping("/products/{productId}/stock-in")
    public ApiResponse<InventoryAccessoryResponse> stockIn(
            @PathVariable String productId,
            @Valid @RequestBody StockInRequest request) {

        return ApiResponse.<InventoryAccessoryResponse>builder()
                .message("Accessory stock imported successfully")
                .results(service.stockIn(productId, request))
                .build();
    }

    @PatchMapping("/products/{productId}/adjust")
    public ApiResponse<InventoryAccessoryResponse> adjust(
            @PathVariable String productId,
            @Valid @RequestBody StockAdjustmentRequest request) {

        return ApiResponse.<InventoryAccessoryResponse>builder()
                .message("Accessory inventory adjusted successfully")
                .results(service.adjust(productId, request))
                .build();
    }

    @PatchMapping("/products/{productId}/deactivate")
    public ApiResponse<Void> deactivate(
            @PathVariable String productId) {

        service.deactivate(productId);

        return ApiResponse.<Void>builder()
                .message("Accessory inventory deactivated successfully")
                .build();
    }

    @GetMapping("/low-stock")
    public ApiResponse<PageResponse<InventoryAccessoryResponse>> getLowStock(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ApiResponse.<PageResponse<InventoryAccessoryResponse>>builder()
                .message("Get low stock accessory inventories successfully")
                .results(service.getLowStock(page, size))
                .build();
    }
}

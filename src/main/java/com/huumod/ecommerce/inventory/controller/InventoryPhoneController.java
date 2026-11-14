package com.huumod.ecommerce.inventory.controller;

import com.huumod.ecommerce.common.ApiResponse;
import com.huumod.ecommerce.common.PageResponse;
import com.huumod.ecommerce.inventory.dto.request.InventoryPhoneRequest;
import com.huumod.ecommerce.inventory.dto.response.InventoryPhoneResponse;
import com.huumod.ecommerce.inventory.service.InventoryPhoneService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryPhoneController {

    InventoryPhoneService inventoryPhoneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<InventoryPhoneResponse> createInventory(
            @RequestBody @Valid InventoryPhoneRequest request) {
        return ApiResponse.<InventoryPhoneResponse>builder()
                .message("Inventory created successfully")
                .results(inventoryPhoneService.createInventory(request))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<InventoryPhoneResponse>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<InventoryPhoneResponse>>builder()
                .message("Get inventories successfully")
                .results(inventoryPhoneService.getALl(page, size))
                .build();
    }

    @PatchMapping("/{id}/increase")
    public ApiResponse<InventoryPhoneResponse> increaseQuantity(@PathVariable String id) {
        return ApiResponse.<InventoryPhoneResponse>builder()
                .message("Increase quantity successfully")
                .results(inventoryPhoneService.increaseQuantity(id))
                .build();
    }

    @PatchMapping("/{id}/reduce")
    public ApiResponse<InventoryPhoneResponse> reduceQuantity(@PathVariable String id) {
        return ApiResponse.<InventoryPhoneResponse>builder()
                .message("Reduce quantity successfully")
                .results(inventoryPhoneService.reduceQuantity(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> removeInventory(@PathVariable String id) {
        inventoryPhoneService.removeInventory(id);
        return ApiResponse.<Void>builder()
                .message("Inventory removed successfully")
                .build();
    }
}
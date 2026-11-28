package com.huumod.ecommerce.inventory.dto.response;

import com.huumod.ecommerce.contract.constant.InventoryStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryPhoneResponse {

    String id;
    String productId;

    Integer quantity;
    Integer reservedQuantity;
    Integer availableQuantity;

    Integer lowStockThreshold;

    InventoryStatus status;

    Boolean lowStock;

    Instant createdAt;
    Instant updatedAt;
}
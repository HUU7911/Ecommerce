package com.huumod.ecommerce.inventory.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventorySummaryResponse {

    Long totalProducts;
    Long totalQuantity;
    Long totalReserved;
    Long totalAvailable;
    Long lowStockProducts;
    Long outOfStockProducts;
}
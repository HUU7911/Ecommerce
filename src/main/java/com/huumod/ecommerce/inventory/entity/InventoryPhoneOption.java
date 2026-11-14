package com.huumod.ecommerce.inventory.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryPhoneOption {
    String color;
    String storage;
    String battery;
    String type;
    String appearance;
    BigDecimal price;
}

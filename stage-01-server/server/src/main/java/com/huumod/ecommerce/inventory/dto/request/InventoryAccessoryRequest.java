package com.huumod.ecommerce.inventory.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryAccessoryRequest {

    String id;

    String  productId;

    String name;

    Integer quantity;

    String status;

    List<String> images;

    String type;

    String price;

    Instant createdAt;

    Instant updatedAt;
}

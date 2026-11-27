package com.huumod.ecommerce.inventory.dto.response;

import com.huumod.ecommerce.inventory.entity.InventoryPhoneOption;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryPhoneResponse {

    String id;

    String productId;

    String name;

    Integer totalQuantity;

    String status;

    List<String> images;

    Set<InventoryPhoneOption> option;

    Instant createdAt;

    Instant updatedAt;
}

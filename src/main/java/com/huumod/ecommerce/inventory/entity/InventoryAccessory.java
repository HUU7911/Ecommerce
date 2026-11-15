package com.huumod.ecommerce.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "inventory_accessory")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryAccessory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String  productId;

    String name;

    Integer quantity;

    String status;

    List<String> images;

    Instant createdAt;

    Instant updatedAt;
}

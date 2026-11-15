package com.huumod.ecommerce.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@Table(name = "inventory_phone_option")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryPhoneOption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    Integer quantity;
    String color;
    String storage;
    String battery;
    String type;
    String appearance;
    BigDecimal price;
}

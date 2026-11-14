package com.huumod.ecommerce.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "inventory_phone")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryPhone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String productId;

    String name;

    @Column(nullable = false)
    Integer quantity;

    @Column(nullable = false)
    String status;

    List<String> images;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    InventoryPhoneOption option;

    Instant createdAt;

    Instant updatedAt;
}

package com.huumod.ecommerce.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.Set;

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
    Integer totalQuantity;

    @Column(nullable = false)
    String status;

    List<String> images;

    @OneToMany(fetch = FetchType.EAGER)
    Set<InventoryPhoneOption> option;

    Instant createdAt;

    Instant updatedAt;
}

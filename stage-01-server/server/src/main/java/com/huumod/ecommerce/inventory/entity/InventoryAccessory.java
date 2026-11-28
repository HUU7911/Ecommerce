package com.huumod.ecommerce.inventory.entity;

import com.huumod.ecommerce.contract.constant.InventoryStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "inventory_accessory",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_inventory_accessory_product",
                columnNames = "product_id"
        ),
        indexes = @Index(name = "idx_inventory_accessory_status", columnList = "status")
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryAccessory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "product_id", nullable = false, unique = true, length = 36)
    String productId;

    @Column(nullable = false)
    @Builder.Default
    Integer quantity = 0;

    @Column(name = "reserved_quantity", nullable = false)
    @Builder.Default
    Integer reservedQuantity = 0;

    @Column(name = "low_stock_threshold", nullable = false)
    @Builder.Default
    Integer lowStockThreshold = 5;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    InventoryStatus status = InventoryStatus.ACTIVE;

    @Version
    @Builder.Default
    Long version = 0L;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    Instant updatedAt;

    @Transient
    public Integer getAvailableQuantity() {
        return quantity - reservedQuantity;
    }
}
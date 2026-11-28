package com.huumod.ecommerce.inventory.entity;

import com.huumod.ecommerce.contract.constant.InventoryTransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phone_inventory_transaction")
public class PhoneInventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private InventoryPhone inventory;

    private String orderId;

    @Enumerated(EnumType.STRING)
    private InventoryTransactionType transactionType;

    private Integer quantityBefore;
    private Integer quantityChanged;
    private Integer quantityAfter;

    private Integer reservedBefore;
    private Integer reservedAfter;

    private String reason;
    private String createdBy;

    @CreationTimestamp
    private Instant createdAt;
}

package com.huumod.ecommerce.inventory.repository;

import com.huumod.ecommerce.contract.constant.InventoryStatus;
import com.huumod.ecommerce.inventory.entity.InventoryAccessory;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryAccessoryRepository extends JpaRepository<InventoryAccessory, String> {

    boolean existsByProductId(String productId);

    Optional<InventoryAccessory> findByProductId(String productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM InventoryAccessory i WHERE i.productId = :productId")
    Optional<InventoryAccessory> findByProductIdForUpdate(@Param("productId") String productId);

    Page<InventoryAccessory> findAllByStatus(InventoryStatus status, Pageable pageable);

    @Query("""
        SELECT i FROM InventoryAccessory i
        WHERE i.status = com.huumod.ecommerce.inventory.enums.InventoryStatus.ACTIVE
        AND i.quantity - i.reservedQuantity <= i.lowStockThreshold
    """)
    Page<InventoryAccessory> findLowStock(Pageable pageable);

    @Query("""
        SELECT i FROM InventoryAccessory i
        WHERE i.status = com.huumod.ecommerce.inventory.enums.InventoryStatus.ACTIVE
        AND i.quantity = i.reservedQuantity
    """)
    Page<InventoryAccessory> findOutOfStock(Pageable pageable);
}
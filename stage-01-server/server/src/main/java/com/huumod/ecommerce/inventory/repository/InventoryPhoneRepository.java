package com.huumod.ecommerce.inventory.repository;

import com.huumod.ecommerce.contract.constant.InventoryStatus;
import com.huumod.ecommerce.inventory.entity.InventoryPhone;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryPhoneRepository extends JpaRepository<InventoryPhone, String> {

    boolean existsByProductId(String productId);

    Optional<InventoryPhone> findByProductId(String productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM InventoryPhone i WHERE i.productId = :productId")
    Optional<InventoryPhone> findByProductIdForUpdate(@Param("productId") String productId);

    Page<InventoryPhone> findAllByStatus(InventoryStatus status, Pageable pageable);

    @Query("""
        SELECT i FROM InventoryPhone i
        WHERE i.status = com.huumod.ecommerce.inventory.enums.InventoryStatus.ACTIVE
        AND i.quantity - i.reservedQuantity <= i.lowStockThreshold
    """)
    Page<InventoryPhone> findLowStock(Pageable pageable);

    @Query("""
        SELECT i FROM InventoryPhone i
        WHERE i.status = com.huumod.ecommerce.inventory.enums.InventoryStatus.ACTIVE
        AND i.quantity = i.reservedQuantity
    """)
    Page<InventoryPhone> findOutOfStock(Pageable pageable);
}

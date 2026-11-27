package com.huumod.ecommerce.inventory.repository;

import com.huumod.ecommerce.inventory.entity.InventoryPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryPhoneRepository extends JpaRepository<InventoryPhone, String> {

    boolean existsByProductId(String productId);

    Optional<InventoryPhone> findByProductId(String productId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE InventoryPhone i SET i.totalQuantity = i.totalQuantity + 1, i.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE i.productId = :productId AND i.totalQuantity < :max")
    int increaseIfNotMax(@Param("productId") String productId, @Param("max") int max);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE InventoryPhone i SET i.totalQuantity = i.totalQuantity - 1, i.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE i.productId = :productId AND i.totalQuantity > 0")
    int decreaseIfAvailable(@Param("productId") String productId);
}

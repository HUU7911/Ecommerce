package com.huumod.ecommerce.inventory.repository;

import com.huumod.ecommerce.inventory.entity.InventoryPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryPhoneRepository extends JpaRepository<InventoryPhone, String> {
    boolean existsByProductId(String productId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE InventoryPhone i SET i.quantity = i.quantity + 1, i.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE i.id = :id AND i.quantity < :max")
    int increaseIfNotMax(@Param("id") String id, @Param("max") int max);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE InventoryPhone i SET i.quantity = i.quantity - 1, i.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE i.id = :id AND i.quantity > 0")
    int decreaseIfAvailable(@Param("id") String id);
}

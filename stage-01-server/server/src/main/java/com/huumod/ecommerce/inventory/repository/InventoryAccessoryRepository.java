package com.huumod.ecommerce.inventory.repository;

import com.huumod.ecommerce.inventory.entity.InventoryAccessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryAccessoryRepository extends JpaRepository<InventoryAccessory, String> {
    boolean existsByProductId(String productId);
}

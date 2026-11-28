package com.huumod.ecommerce.inventory.repository;

import com.huumod.ecommerce.inventory.entity.PhoneInventoryTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneInventoryTransactionRepository extends JpaRepository<PhoneInventoryTransaction, String> {

    Page<PhoneInventoryTransaction> findAllByInventory_IdOrderByCreatedAtDesc(String inventoryId, Pageable pageable);
    Page<PhoneInventoryTransaction> findAllByOrderId(String orderId, Pageable pageable);
}

package com.huumod.ecommerce.inventory.repository;

import com.huumod.ecommerce.inventory.entity.InventoryPhoneOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryPhoneOptionRepository extends JpaRepository<InventoryPhoneOption, String> {
}

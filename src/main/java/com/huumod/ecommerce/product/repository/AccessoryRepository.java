package com.huumod.ecommerce.product.repository;

import com.huumod.ecommerce.product.entity.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, String> {
    boolean existsByName(String name);
}

package com.huumod.ecommerce.product.repository;

import com.huumod.ecommerce.product.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, String> {
    boolean existsByName(String name);
}

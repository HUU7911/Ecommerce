package com.huumod.ecommerce.product.repository;

import com.huumod.ecommerce.product.entity.PhoneDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneDetailRepository extends JpaRepository<PhoneDetail, Long> {
}

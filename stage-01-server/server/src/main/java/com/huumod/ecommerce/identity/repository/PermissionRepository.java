package com.huumod.ecommerce.identity.repository;

import com.huumod.ecommerce.identity.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    boolean existsByName(String name);
}

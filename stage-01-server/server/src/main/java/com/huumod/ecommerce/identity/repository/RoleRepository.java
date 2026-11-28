package com.huumod.ecommerce.identity.repository;

import com.huumod.ecommerce.identity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    boolean existsByName(String name);
    List<Role> findAllById(List<String> name);
}

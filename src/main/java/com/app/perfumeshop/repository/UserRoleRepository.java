package com.app.perfumeshop.repository;

import com.app.perfumeshop.model.entity.Product;
import com.app.perfumeshop.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}

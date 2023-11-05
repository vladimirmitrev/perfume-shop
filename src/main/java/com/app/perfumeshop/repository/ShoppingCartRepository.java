package com.app.perfumeshop.repository;

import com.app.perfumeshop.model.entity.Brand;
import com.app.perfumeshop.model.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}

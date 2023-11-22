package com.app.perfumeshop.repository;

import com.app.perfumeshop.model.entity.Brand;
import com.app.perfumeshop.model.entity.ShoppingCart;
import com.app.perfumeshop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findShoppingCartByCustomer_Id(Long id);
    List<ShoppingCart> findByCustomer(User user);
}

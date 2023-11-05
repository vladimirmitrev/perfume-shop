package com.app.perfumeshop.repository;

import com.app.perfumeshop.model.entity.Brand;
import com.app.perfumeshop.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}

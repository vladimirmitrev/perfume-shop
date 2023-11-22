package com.app.perfumeshop.repository;

import com.app.perfumeshop.model.entity.Brand;
import com.app.perfumeshop.model.entity.Order;
import com.app.perfumeshop.model.enums.OrderStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o" +
            " WHERE o.status = :status")
    List<Order> findAllByOrderByStatus(@Param("status") OrderStatusEnum status);

    //    @Modifying
//    @Transactional
//    @Query("DELETE FROM Order o" +
//            " WHERE o.status = :status")
//    void deleteAllCanceledOrders(@Param("status") OrderStatusEnum status);
}

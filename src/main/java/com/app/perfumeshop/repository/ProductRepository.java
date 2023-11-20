package com.app.perfumeshop.repository;

import com.app.perfumeshop.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    @Query("SELECT p FROM Product p")
//    Page<Product> pageProduct(Pageable pageable);
    @Query("SELECT p " +
            "FROM Product p" +
            " WHERE p.brand.name LIKE %?1% OR p.name LIKE %?1% OR p.description LIKE %?1%")
    List<Product> searchProductsByBrandOrNameOrDescription(String keyword);
}

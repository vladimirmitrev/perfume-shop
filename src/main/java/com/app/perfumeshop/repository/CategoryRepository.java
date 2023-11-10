package com.app.perfumeshop.repository;

import com.app.perfumeshop.model.entity.Category;
import com.app.perfumeshop.model.enums.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(CategoryNameEnum name);
}
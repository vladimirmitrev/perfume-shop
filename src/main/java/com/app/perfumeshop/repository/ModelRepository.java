package com.app.perfumeshop.repository;

import com.app.perfumeshop.model.entity.Model;
import com.app.perfumeshop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
}

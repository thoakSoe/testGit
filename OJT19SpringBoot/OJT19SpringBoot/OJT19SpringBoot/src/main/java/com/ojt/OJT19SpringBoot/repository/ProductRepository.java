package com.ojt.OJT19SpringBoot.repository;

import com.ojt.OJT19SpringBoot.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategory(String category);
    List<ProductEntity> findByPriceGreaterThan(Double price);

}

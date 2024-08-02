package com.example.zerodang.domain.product.repository;

import com.example.zerodang.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Modifying
    @Query("UPDATE Product p SET p.views = 0")
    void resetAllProductViews();
}

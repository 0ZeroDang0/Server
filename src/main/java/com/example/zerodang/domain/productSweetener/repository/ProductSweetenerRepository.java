package com.example.zerodang.domain.productSweetener.repository;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.productSweetener.entity.ProductSweetener;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSweetenerRepository extends JpaRepository<ProductSweetener, Long> {
    List<ProductSweetener> findByProduct(Product product);
}

package com.example.zerodang.domain.productLike.repository;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.productLike.entity.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {
    Optional<ProductLike> findByProductAndUserId(Product product, Long userId);
}

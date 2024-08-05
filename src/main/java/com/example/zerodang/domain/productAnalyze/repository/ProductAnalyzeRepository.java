package com.example.zerodang.domain.productAnalyze.repository;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.productAnalyze.entity.ProductAnalyze;
import com.example.zerodang.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductAnalyzeRepository extends JpaRepository<ProductAnalyze, Long>, ProductAnalyzeRepositoryCustom {
    Optional<ProductAnalyze> findByProductAndUser(Product product, User user);
}

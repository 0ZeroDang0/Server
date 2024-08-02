package com.example.zerodang.domain.sweetener.repository;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.sweetener.entity.Sweetener;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SweetenerRepository extends JpaRepository<Sweetener, Long> {
    List<Sweetener> findByProduct(Product product);
}

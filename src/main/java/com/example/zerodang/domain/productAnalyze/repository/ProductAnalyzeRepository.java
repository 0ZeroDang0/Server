package com.example.zerodang.domain.productAnalyze.repository;

import com.example.zerodang.domain.productAnalyze.entity.ProductAnalyze;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAnalyzeRepository extends JpaRepository<ProductAnalyze, Long>, ProductAnalyzeRepositoryCustom {
}

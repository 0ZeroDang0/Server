package com.example.zerodang.domain.comparison.repository;

import com.example.zerodang.domain.comparison.entity.Comparison;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComparisonRepository extends JpaRepository<Comparison, Long> {
    Optional<Comparison> findByProduct1AndProduct2(Long product1, Long product2);
    List<Comparison> findTop3ByOrderByViewsDesc();
}

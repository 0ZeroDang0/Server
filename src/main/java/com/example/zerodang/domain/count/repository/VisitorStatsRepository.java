package com.example.zerodang.domain.count.repository;

import com.example.zerodang.domain.count.entity.VisitorStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface VisitorStatsRepository extends JpaRepository<VisitorStats, Long> {
}

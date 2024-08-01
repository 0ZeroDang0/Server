package com.example.zerodang.domain.visitorStats.repository;

import com.example.zerodang.domain.visitorStats.entity.VisitorStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface VisitorStatsRepository extends JpaRepository<VisitorStats, Long> {
    Optional<VisitorStats> findByVisitDate(LocalDate visitDate);
}

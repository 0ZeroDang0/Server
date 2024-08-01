package com.example.zerodang.domain.visitorStats.service;

import com.example.zerodang.domain.visitorStats.dto.response.VisitorStatsResponseDTO;
import com.example.zerodang.domain.visitorStats.entity.VisitorStats;
import com.example.zerodang.domain.visitorStats.repository.VisitorStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class VisitorStatsServiceImpl implements VisitorStatsService {
    private final VisitorStatsRepository visitorStatsRepository;

    @Override
    public void recordVisit() {
        LocalDate today = LocalDate.now();
        Optional<VisitorStats> optionalStats = visitorStatsRepository.findByVisitDate(today);
        if (optionalStats.isPresent()) {
            optionalStats.get().plusVisitCount();
        } else {
            VisitorStats visitorStats = VisitorStats.builder().visitDate(today).visitCount(1).build();
            visitorStatsRepository.save(visitorStats);
        }
    }

    @Override
    public VisitorStatsResponseDTO.VisitorStatsCountDTO findVisitorStatsCount() {
        return VisitorStatsResponseDTO.VisitorStatsCountDTO.builder().totalCount(getTotalVisitCount()).todayCount(getTodayVisitCount())
                .build();
    }

    public int getTotalVisitCount() {
        return visitorStatsRepository.findAll().stream()
                .mapToInt(VisitorStats::getVisitCount)
                .sum();
    }

    public int getTodayVisitCount() {
        LocalDate today = LocalDate.now();
        return visitorStatsRepository.findByVisitDate(today)
                .map(VisitorStats::getVisitCount)
                .orElse(0);
    }
}

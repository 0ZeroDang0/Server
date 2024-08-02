package com.example.zerodang.domain.count.service;

import com.example.zerodang.domain.count.dto.response.VisitorStatsResponseDTO;
import com.example.zerodang.domain.count.entity.VisitorStats;
import com.example.zerodang.domain.count.repository.VisitorStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CountServiceServiceImpl implements CountService {
    private final VisitorStatsRepository visitorStatsRepository;
    private final RedisTemplate<String, Integer> redisTemplate;

    private static final String TODAY_VISITS_KEY = "todayVisits";
    private static final String TODAY_COMPARISONS_KEY = "todayComparisons";

    @Override
    @Transactional
    public void recordVisit() {
        redisTemplate.opsForValue().increment(TODAY_VISITS_KEY);
        VisitorStats stats = visitorStatsRepository.findById(1L)
                .orElseGet(() -> new VisitorStats(1L, 0));
        stats.setTotalVisitCount();
    }
    @Override
    @Transactional
    public void recordComparison() {
        redisTemplate.opsForValue().increment(TODAY_COMPARISONS_KEY);
    }

    @Override
    public VisitorStatsResponseDTO.VisitorStatsCountDTO findCount() {
        return VisitorStatsResponseDTO.VisitorStatsCountDTO.builder()
                .totalVisitorCount(getTotalVisitCount())
                .todayVisitorCount(getTodayVisitCount())
                .todayComparisonCount(getTodayComparisonCount())
                .build();
    }

    public int getTotalVisitCount() {
        return visitorStatsRepository.findAll().stream()
                .mapToInt(VisitorStats::getTotalVisitCount)
                .sum();
    }

    public int getTodayVisitCount() {
        Integer count = redisTemplate.opsForValue().get(TODAY_VISITS_KEY);
        return count == null ? 0 : count;
    }


    public int getTodayComparisonCount() {
        Integer count = redisTemplate.opsForValue().get(TODAY_COMPARISONS_KEY);
        return count == null ? 0 : count;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetDailyCounts() {
        redisTemplate.delete(TODAY_VISITS_KEY);
        redisTemplate.delete(TODAY_COMPARISONS_KEY);
    }

}

package com.example.zerodang.domain.count.service;

import com.example.zerodang.domain.count.dto.response.VisitorStatsResponseDTO;

public interface CountService {
    void recordVisit();
    void recordComparison();
    VisitorStatsResponseDTO.VisitorStatsCountDTO findCount();
}

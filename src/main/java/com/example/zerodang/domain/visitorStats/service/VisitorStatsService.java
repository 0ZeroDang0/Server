package com.example.zerodang.domain.visitorStats.service;

import com.example.zerodang.domain.visitorStats.dto.response.VisitorStatsResponseDTO;

public interface VisitorStatsService {
    public void recordVisit();
    VisitorStatsResponseDTO.VisitorStatsCountDTO findVisitorStatsCount();
}

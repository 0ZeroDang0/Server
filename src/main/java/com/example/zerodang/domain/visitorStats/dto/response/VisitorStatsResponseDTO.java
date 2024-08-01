package com.example.zerodang.domain.visitorStats.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class VisitorStatsResponseDTO {

    @Builder
    @Data
    @AllArgsConstructor
    public static class VisitorStatsCountDTO {
        int totalCount;
        int todayCount;
    }
}

package com.example.zerodang.domain.productAnalyze.dto.request;

import lombok.Data;

public class ProductAnalyzeRequestDTO {
    @Data
    public static class ProductAnalyzeComparisonDTO {
        private Long productId1;
        private Long productId2;
    }
}

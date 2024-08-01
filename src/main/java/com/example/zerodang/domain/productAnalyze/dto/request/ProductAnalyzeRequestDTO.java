package com.example.zerodang.domain.productAnalyze.dto.request;

import lombok.Data;

import java.util.List;

public class ProductAnalyzeRequestDTO {
    @Data
    public static class ProductAnalyzeComparisonDTO {
        private Long productId1;
        private Long productId2;
    }
    @Data
    public static class ProductAnalyzeDeleteDTO {
        private List<Long> productAnalyzeLists;
    }
}

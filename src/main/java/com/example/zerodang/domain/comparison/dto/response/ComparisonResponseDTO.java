package com.example.zerodang.domain.comparison.dto.response;

import lombok.*;

import java.util.List;

public class ComparisonResponseDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ComparisonDetailDTO {
        private Long productId1;
        private String productName1;
        private String thumbnail1;
        private Long productId2;
        private String productName2;
        private String thumbnail2;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ComparisonTOP3DTO {
        private List<ComparisonDetailDTO> comparisonDetailDTOList;
    }
}

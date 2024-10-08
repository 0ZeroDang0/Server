package com.example.zerodang.domain.productAnalyze.dto.response;

import com.example.zerodang.domain.product.entity.ProductCategory;
import com.example.zerodang.domain.reviewKeyword.entity.Keyword;
import com.example.zerodang.domain.sweetener.entity.Sweetener;
import lombok.*;

import java.util.List;
import java.util.Map;

public class ProductAnalyzeResponseDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ProductAnalyzeSaveDTO {
        private Long productAnalyzeId;
        private Long userId;
        private String userName;
        private Long productId;
        private String productName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ProductAnalyzeFindOneDTO {
        private Long productId;
        private Long productAnalyzeId;
        private String productName;
        private int productMl;
        private ProductCategory productCategory;
        private Map<Keyword, Long> keywordList;
        private String thumbnail;

        public ProductAnalyzeFindOneDTO(Long productId, Long productAnalyzeId, String productName, int productMl, ProductCategory productCategory, String thumbnail) {
            this.productId = productId;
            this.productAnalyzeId = productAnalyzeId;
            this.productName = productName;
            this.productMl = productMl;
            this.productCategory = productCategory;
            this.thumbnail = thumbnail;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ProductAnalyzeFindDetailDTO {
        private Long productId;
        private String productName;
        private ProductCategory productCategory;
        private String productImageUrl;
        private float kcal;
        private int productMl;
        private float carbohydrateG;
        private float sugarG;
        private float proteinG;
        private float fatG;
        private float saturatedFatG;
        private float transFatG;
        private float cholesterolG;
        private float sodiumG;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ProductAnalyzeResultDTO {
        private List<ProductAnalyzeFindDetailDTO> productAnalyzeFindDetailDTOList;
        private List<Sweetener> sweetenerList; // 이 부분 product 데이터 들어감
        private String resultComment;
    }
}

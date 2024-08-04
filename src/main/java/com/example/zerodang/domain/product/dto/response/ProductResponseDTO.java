package com.example.zerodang.domain.product.dto.response;

import com.example.zerodang.domain.product.entity.ProductCategory;
import com.example.zerodang.domain.reviewKeyword.entity.Keyword;
import com.example.zerodang.domain.sweetener.entity.Sweetener;
import lombok.*;

import java.util.List;
import java.util.Map;

public class ProductResponseDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ProductFindOneDTO {
        private Long productId;
        private String productName;
        private int productMl;
        private ProductCategory productCategory;
        private Map<Keyword, Long> keywordList;
        private String thumbnail;

        public ProductFindOneDTO(Long productId, String productName, int productMl, ProductCategory productCategory, String thumbnail) {
            this.productId = productId;
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
    public static class ProductFindAllDTO {
        private List<ProductFindOneDTO> productFindOneList;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ProductDetailDTO {
        private Long productId;
        private String productName;
        private ProductCategory productCategory;
        private String thumbnail;
        private int productMl;
        private double stars;
        private int likes;
        private List<Sweetener> sweetenerList;
        private Map<Keyword, Long> keywordList;
        private float kcal;
        private float carbohydrateG;
        private float sugarG;
        private float proteinG;
        private float fatG;
        private float saturatedFatG;
        private float transFatG;
        private float cholesterolG;
        private float sodiumG;
        private String productDescription;

        public ProductDetailDTO(Long productId, String productName, ProductCategory productCategory, String thumbnail, int productMl, double stars, int likes, float kcal, float carbohydrateG, float sugarG, float proteinG, float fatG, float saturatedFatG, float transFatG, float cholesterolG, float sodiumG, String productDescription) {
            this.productId = productId;
            this.productName = productName;
            this.productCategory = productCategory;
            this.thumbnail = thumbnail;
            this.productMl = productMl;
            this.stars = stars;
            this.likes = likes;
            this.kcal = kcal;
            this.carbohydrateG = carbohydrateG;
            this.sugarG = sugarG;
            this.proteinG = proteinG;
            this.fatG = fatG;
            this.saturatedFatG = saturatedFatG;
            this.transFatG = transFatG;
            this.cholesterolG = cholesterolG;
            this.sodiumG = sodiumG;
            this.productDescription = productDescription;
        }
// 감미료 상세 정보 어떻게 할 건지 고아열이랑 대화 나누기
    }
}

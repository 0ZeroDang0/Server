package com.example.zerodang.domain.product.dto.response;

import lombok.*;

import java.util.List;

public class ProductResponseDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ProductFindOneDTO {

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
    }
}

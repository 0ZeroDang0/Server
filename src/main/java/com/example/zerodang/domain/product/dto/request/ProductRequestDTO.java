package com.example.zerodang.domain.product.dto.request;

import com.example.zerodang.domain.product.entity.ProductCategory;
import lombok.Builder;
import lombok.Data;

public class ProductRequestDTO {
    @Data
    @Builder
    public static class ProductFilterDTO {
        private String keyWord;
        private ProductCategory productCategory;
    }
}

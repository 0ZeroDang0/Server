package com.example.zerodang.domain.article.dto.response;

import lombok.*;

public class ArticleResponseDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ArticleFindAllDTO {
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ArticleFindDetailDTO {
    }
}

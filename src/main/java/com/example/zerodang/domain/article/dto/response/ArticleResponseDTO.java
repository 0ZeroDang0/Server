package com.example.zerodang.domain.article.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleResponseDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ArticleFindOneDTO {
        private Long articleId;
        private String title;
        private String author;
        private String summary;
        private LocalDateTime date;
        private String hashTag;
        private int views;
        private int likes;
        private String thumbnail;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ArticleFindAllDTO {
        private List<ArticleFindOneDTO> articleFindOneDTOList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ArticleFindDetailDTO {
        private Long articleId;
        private String title;
        private String author;
        private String summary;
        private String content;
        private LocalDateTime date;
        private String hashTag;
        private int views;
        private int likes;
        private String thumbnail;
        private List<String> articleImageList;
    }
}

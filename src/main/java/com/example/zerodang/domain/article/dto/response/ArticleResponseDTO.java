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

        public ArticleFindDetailDTO(Long articleId, String title, String author, String summary, String content, LocalDateTime date, String hashTag, int views, int likes, String thumbnail) {
            this.articleId = articleId;
            this.title = title;
            this.author = author;
            this.summary = summary;
            this.content = content;
            this.date = date;
            this.hashTag = hashTag;
            this.views = views;
            this.likes = likes;
            this.thumbnail = thumbnail;
        }
    }
}

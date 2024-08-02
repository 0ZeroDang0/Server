package com.example.zerodang.domain.review.dto.response;

import com.example.zerodang.domain.reviewKeyword.entity.ReviewKeyword;
import lombok.*;

import java.util.List;

public class ReviewResponseDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class KeyWordDTO {
        private Long keywordId;
        private String keyword;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ReviewDetailDTO {
        private Long userId;
        private String userName;
        private int rating;
        private List<KeyWordDTO> keyWordDTOList;
        public ReviewDetailDTO(Long userId, String userName, int rating) {
            this.userId = userId;
            this.userName = userName;
            this.rating = rating;
        }
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ReviewFindCountDTO {
        private int reviews;
        private int oneStarReviews;
        private int twoStarReviews;
        private int threeStarReviews;
        private int fourStarReviews;
        private int fiveStarReviews;
    }
}

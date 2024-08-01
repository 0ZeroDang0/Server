package com.example.zerodang.domain.review.dto.response;

import com.example.zerodang.domain.reviewKeyword.entity.ReviewKeyword;
import lombok.*;

import java.util.List;

public class ReviewResponseDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ReviewDetailDTO {
        private int userId;
        private String userName;
        private int rating;
        private List<ReviewKeyword> reviewKeywordList;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ReviewFindAllDTO {
        private int reviews;
        private int oneStarReviews;
        private int twoStarReviews;
        private int threeStarReviews;
        private int fourStarReviews;
        private int fiveStarReviews;
        List<ReviewDetailDTO> reviewDetailDTOList;
    }
}

package com.example.zerodang.domain.review.dto.request;

import com.example.zerodang.domain.reviewKeyword.entity.Keyword;
import lombok.Data;

import java.util.List;

public class ReviewRequestDTO {
    @Data
    public static class ReviewSaveDTO {
        private Long productId;
        private int rating;
        private List<Keyword> keywordList;
    }
}

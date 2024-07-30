package com.example.zerodang.domain.review.dto.request;

import com.example.zerodang.domain.review.entity.Keyword;
import lombok.Data;

import java.util.List;

public class ReviewRequestDTO {
    @Data
    public static class ReviewSaveDTO {
        private int rating;
        private List<Keyword> keywordList;
    }
}

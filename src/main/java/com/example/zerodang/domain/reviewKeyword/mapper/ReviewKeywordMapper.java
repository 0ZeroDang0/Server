package com.example.zerodang.domain.reviewKeyword.mapper;

import com.example.zerodang.domain.reviewKeyword.entity.Keyword;
import com.example.zerodang.domain.review.entity.Review;
import com.example.zerodang.domain.reviewKeyword.entity.ReviewKeyword;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewKeywordMapper {
    public List<ReviewKeyword> toReviewKeywordEntities(Review review, List<Keyword> keywordList) {
        return keywordList.stream()
                .map(keyword -> ReviewKeyword.builder()
                        .keyword(keyword)
                        .review(review)
                        .build())
                .collect(Collectors.toList());
    }
}

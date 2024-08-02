package com.example.zerodang.domain.reviewKeyword.service;

import com.example.zerodang.domain.reviewKeyword.entity.Keyword;
import com.example.zerodang.domain.review.entity.Review;
import com.example.zerodang.domain.reviewKeyword.entity.ReviewKeyword;

import java.util.List;

public interface ReviewKeywordService {
    List<ReviewKeyword> saveReviewKeywords(Review review, List<Keyword> keywordList);
}

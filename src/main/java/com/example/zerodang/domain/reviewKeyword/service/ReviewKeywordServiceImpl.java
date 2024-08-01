package com.example.zerodang.domain.reviewKeyword.service;

import com.example.zerodang.domain.reviewKeyword.entity.Keyword;
import com.example.zerodang.domain.review.entity.Review;
import com.example.zerodang.domain.reviewKeyword.entity.ReviewKeyword;
import com.example.zerodang.domain.reviewKeyword.mapper.ReviewKeywordMapper;
import com.example.zerodang.domain.reviewKeyword.repository.ReviewKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ReviewKeywordServiceImpl implements ReviewKeywordService {
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final ReviewKeywordMapper reviewKeywordMapper;

    @Override
    @Transactional
    public List<ReviewKeyword> saveReviewKeywords(Review review) {
        List<Keyword> keywordList = new ArrayList<>();
        return reviewKeywordRepository.saveAll(reviewKeywordMapper.toReviewKeywordEntities(review, keywordList));
    }
}

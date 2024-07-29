package com.example.zerodang.domain.review.service;

import com.example.zerodang.domain.review.dto.request.ReviewRequestDTO;
import com.example.zerodang.domain.review.dto.response.ReviewResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    @Override
    public ReviewResponseDTO.ReviewSaveDTO save(ReviewRequestDTO.ReviewSaveDTO reviewSaveDTO) {
        return null;
    }

    @Override
    public ReviewResponseDTO.ReviewFindAllDTO findAllByProductId(Long productId) {
        return null;
    }
}

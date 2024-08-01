package com.example.zerodang.domain.review.repository;

import com.example.zerodang.domain.review.dto.response.ReviewResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<ReviewResponseDTO.ReviewDetailDTO> findAllByProductId(Long productId, Pageable pageable);
    ReviewResponseDTO.ReviewFindCountDTO countReviewsByProductId(Long productId);
}

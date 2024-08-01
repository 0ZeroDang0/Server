package com.example.zerodang.domain.review.service;

import com.example.zerodang.domain.review.dto.request.ReviewRequestDTO;
import com.example.zerodang.domain.review.dto.response.ReviewResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    /** 리뷰 저장 **/
    ReviewResponseDTO.ReviewDetailDTO save(ReviewRequestDTO.ReviewSaveDTO reviewSaveDTO, Long userId);

    /** 상품별 리뷰 전체 조회 **/
    Page<ReviewResponseDTO.ReviewDetailDTO> findAllByProductId(Long productId, Pageable pageable);

    /** 상품별 리뷰 데이터 전체 조회 **/
    ReviewResponseDTO.ReviewFindCountDTO findCountByProductId(Long productId);
}

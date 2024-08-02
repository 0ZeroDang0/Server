package com.example.zerodang.domain.review.service;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.product.service.ProductService;
import com.example.zerodang.domain.review.dto.request.ReviewRequestDTO;
import com.example.zerodang.domain.review.dto.response.ReviewResponseDTO;
import com.example.zerodang.domain.review.entity.Review;
import com.example.zerodang.domain.reviewKeyword.entity.ReviewKeyword;
import com.example.zerodang.domain.review.mapper.ReviewMapper;
import com.example.zerodang.domain.review.repository.ReviewRepository;
import com.example.zerodang.domain.reviewKeyword.service.ReviewKeywordService;
import com.example.zerodang.domain.user.entity.User;
import com.example.zerodang.domain.user.service.UserService;
import com.example.zerodang.global.exception.ErrorCode;
import com.example.zerodang.global.exception.review.ReviewDuplicateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewKeywordService reviewKeywordService;
    private final UserService userService;
    private final ProductService productService;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewResponseDTO.ReviewDetailDTO save(ReviewRequestDTO.ReviewSaveDTO reviewSaveDTO, Long userId) {
        User findUser = userService.getUser_Id(userId);
        Product findProduct = productService.getProduct_id(reviewSaveDTO.getProductId());
        checkReview(findUser, findProduct);

        Review review = reviewMapper.toReviewEntity(reviewSaveDTO, productService.getProduct_id(reviewSaveDTO.getProductId()), userService.getUser_Id(userId));
        reviewRepository.save(review);
        List<ReviewKeyword> reviewKeywords = reviewKeywordService.saveReviewKeywords(review, reviewSaveDTO.getKeywordList());
        return reviewMapper.toReviewDetailDTO(review, reviewKeywords);
    }

    @Override
    public Page<ReviewResponseDTO.ReviewDetailDTO> findAllByProductId(Long productId, Pageable pageable) {
        return reviewRepository.findAllByProductId(productId, pageable);

    }

    @Override
    public ReviewResponseDTO.ReviewFindCountDTO findCountByProductId(Long productId) {
        return reviewRepository.countReviewsByProductId(productId);
    }
    private void checkReview(User user, Product product) {
        if (reviewRepository.findByUserAndProduct(user, product).isPresent()) {
            throw new ReviewDuplicateException(ErrorCode.DUPLICATE_REVIEW);
        }
    }
}

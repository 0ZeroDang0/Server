package com.example.zerodang.domain.review.repository;

import com.example.zerodang.domain.review.dto.response.ReviewResponseDTO;
import com.example.zerodang.domain.review.entity.QReview;
import com.example.zerodang.domain.review.entity.Review;
import com.example.zerodang.domain.reviewKeyword.entity.QReviewKeyword;
import com.example.zerodang.domain.reviewKeyword.entity.ReviewKeyword;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.zerodang.domain.review.entity.QReview.review;

public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ReviewResponseDTO.ReviewDetailDTO> findAllByProductId(Long productId, Pageable pageable) {
        QReview review = QReview.review;
        QReviewKeyword reviewKeyword = QReviewKeyword.reviewKeyword;

        List<Review> reviews = queryFactory.selectFrom(review)
                .where(review.product.productId.eq(productId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ReviewResponseDTO.ReviewDetailDTO> reviewDetailDTOList = reviews.stream()
                .map(r -> {
                    List<ReviewKeyword> reviewKeywords = queryFactory.selectFrom(reviewKeyword)
                            .where(reviewKeyword.review.reviewId.eq(r.getReviewId()))
                            .fetch();

                    return ReviewResponseDTO.ReviewDetailDTO.builder()
                            .userId(r.getUser().getUserId().intValue())
                            .userName(r.getUser().getUserName())
                            .rating(r.getRating())
                            .reviewKeywordList(reviewKeywords)
                            .build();
                })
                .collect(Collectors.toList());

        long total = queryFactory.selectFrom(review)
                .where(review.product.productId.eq(productId))
                .fetchCount();

        return new PageImpl<>(reviewDetailDTOList, pageable, total);
    }

    @Override
    public ReviewResponseDTO.ReviewFindCountDTO countReviewsByProductId(Long productId) {
        long totalReviews = queryFactory.selectFrom(review)
                .where(review.product.productId.eq(productId))
                .fetchCount();

        long oneStarReviews = queryFactory.selectFrom(review)
                .where(review.product.productId.eq(productId)
                        .and(review.rating.eq(1)))
                .fetchCount();

        long twoStarReviews = queryFactory.selectFrom(review)
                .where(review.product.productId.eq(productId)
                        .and(review.rating.eq(2)))
                .fetchCount();

        long threeStarReviews = queryFactory.selectFrom(review)
                .where(review.product.productId.eq(productId)
                        .and(review.rating.eq(3)))
                .fetchCount();

        long fourStarReviews = queryFactory.selectFrom(review)
                .where(review.product.productId.eq(productId)
                        .and(review.rating.eq(4)))
                .fetchCount();

        long fiveStarReviews = queryFactory.selectFrom(review)
                .where(review.product.productId.eq(productId)
                        .and(review.rating.eq(5)))
                .fetchCount();

        return ReviewResponseDTO.ReviewFindCountDTO.builder()
                .reviews((int) totalReviews)
                .oneStarReviews((int) oneStarReviews)
                .twoStarReviews((int) twoStarReviews)
                .threeStarReviews((int) threeStarReviews)
                .fourStarReviews((int) fourStarReviews)
                .fiveStarReviews((int) fiveStarReviews)
                .build();
    }
}

package com.example.zerodang.domain.productAnalyze.repository;

import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import com.example.zerodang.domain.product.entity.QProduct;
import com.example.zerodang.domain.productAnalyze.dto.response.ProductAnalyzeResponseDTO;
import com.example.zerodang.domain.productAnalyze.entity.QProductAnalyze;
import com.example.zerodang.domain.reviewKeyword.entity.Keyword;
import com.example.zerodang.domain.reviewKeyword.entity.QReviewKeyword;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.zerodang.domain.product.entity.QProduct.product;
import static com.example.zerodang.domain.productAnalyze.entity.QProductAnalyze.productAnalyze;
import static com.example.zerodang.domain.reviewKeyword.entity.QReviewKeyword.reviewKeyword;

public class ProductAnalyzeRepositoryImpl implements ProductAnalyzeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ProductAnalyzeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public Page<ProductAnalyzeResponseDTO.ProductAnalyzeFindOneDTO> findAllByUserIdWithPageable(Long userId, Pageable pageable) {
        QProduct product = QProduct.product;
        QProductAnalyze productAnalyze = QProductAnalyze.productAnalyze;
        QReviewKeyword reviewKeyword = QReviewKeyword.reviewKeyword;

        List<ProductAnalyzeResponseDTO.ProductAnalyzeFindOneDTO> result = queryFactory.select(Projections.constructor(ProductAnalyzeResponseDTO.ProductAnalyzeFindOneDTO.class,
                        product.productId,
                        product.productName,
                        product.productMl,
                        product.productCategory,
                        product.thumbnail
                ))
                .from(productAnalyze)
                .leftJoin(productAnalyze.product, product)
                .where(productAnalyze.user.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        result.forEach(productDTO -> {
            Map<Keyword, Long> keywordCountMap = queryFactory.select(reviewKeyword.keyword, reviewKeyword.keyword.count())
                    .from(reviewKeyword)
                    .where(reviewKeyword.review.product.productId.eq(productDTO.getProductId()))
                    .groupBy(reviewKeyword.keyword)
                    .fetch()
                    .stream()
                    .collect(Collectors.toMap(tuple -> tuple.get(reviewKeyword.keyword), tuple -> tuple.get(reviewKeyword.keyword.count())));

            for (Keyword keyword : Keyword.values()) {
                keywordCountMap.putIfAbsent(keyword, 0L);
            }

            productDTO.setKeywordList(keywordCountMap);
        });

        long total = queryFactory.selectFrom(productAnalyze)
                .where(productAnalyze.user.userId.eq(userId))
                .fetchCount();

        return new PageImpl<>(result, pageable, total);
    }

}

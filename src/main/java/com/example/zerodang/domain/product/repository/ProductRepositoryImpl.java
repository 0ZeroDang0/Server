package com.example.zerodang.domain.product.repository;

import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import com.example.zerodang.domain.product.entity.ProductCategory;
import com.example.zerodang.domain.product.entity.QProduct;
import com.example.zerodang.domain.review.entity.Keyword;
import com.example.zerodang.domain.review.entity.QReviewKeyword;
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

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ProductResponseDTO.ProductFindOneDTO> findAllWithPageable(ProductCategory productCategory, Pageable pageable) {
        QProduct product = QProduct.product;
        QReviewKeyword reviewKeyword = QReviewKeyword.reviewKeyword;

        List<ProductResponseDTO.ProductFindOneDTO> result = queryFactory.select(Projections.constructor(ProductResponseDTO.ProductFindOneDTO.class,
                        product.productId,
                        product.productName,
                        product.productMl,
                        product.productCategory,
                        product.thumbnail
                ))
                .from(product)
                .where(product.productCategory.eq(productCategory))
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

        long total = queryFactory.selectFrom(product)
                .where(product.productCategory.eq(productCategory))
                .fetchCount();

        return new PageImpl<>(result, pageable, total);
    }

}

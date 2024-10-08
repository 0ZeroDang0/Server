package com.example.zerodang.domain.product.repository;

import com.example.zerodang.domain.product.dto.request.ProductRequestDTO;
import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import com.example.zerodang.domain.product.entity.ProductCategory;
import com.example.zerodang.domain.reviewKeyword.entity.Keyword;
import com.example.zerodang.domain.sweetener.entity.Sweetener;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static com.example.zerodang.domain.product.entity.QProduct.product;
import static com.example.zerodang.domain.productSweetener.entity.QProductSweetener.productSweetener;
import static com.example.zerodang.domain.sweetener.entity.QSweetener.sweetener;
import static com.example.zerodang.domain.reviewKeyword.entity.QReviewKeyword.reviewKeyword;
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ProductResponseDTO.ProductFindOneDTO> findAllByCategoryWithPageable(ProductCategory productCategory, Pageable pageable) {
        BooleanExpression categoryFilter = (productCategory != null) ? product.productCategory.eq(productCategory) : null;

        List<ProductResponseDTO.ProductFindOneDTO> result = queryFactory.select(Projections.constructor(ProductResponseDTO.ProductFindOneDTO.class,
                        product.productId,
                        product.productName,
                        product.productMl,
                        product.productCategory,
                        product.thumbnail
                ))
                .from(product)
                .where(categoryFilter)
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
                .where(categoryFilter)
                .fetchCount();

        return new PageImpl<>(result, pageable, total);
    }


    @Override
    public ProductResponseDTO.ProductDetailDTO findDetailByProductId(Long productId) {
        ProductResponseDTO.ProductDetailDTO result = queryFactory.select(Projections.constructor(ProductResponseDTO.ProductDetailDTO.class,
                        product.productId,
                        product.productName,
                        product.productCategory,
                        product.thumbnail,
                        product.productMl,
                        product.stars,
                        product.likes,
                        product.kcal,
                        product.carbohydrateG,
                        product.sugarG,
                        product.proteinG,
                        product.fatG,
                        product.saturatedFatG,
                        product.transFatG,
                        product.cholesterolG,
                        product.sodiumG,
                        product.productDescription
                ))
                .from(product)
                .where(product.productId.eq(productId))
                .fetchOne();

        if (result != null) {
            List<Sweetener> sweetenerList = queryFactory.select(sweetener)
                    .from(productSweetener)
                    .join(productSweetener.sweetener, sweetener)
                    .where(productSweetener.product.productId.eq(productId))
                    .fetch();
            result.setSweetenerList(sweetenerList);

            Map<Keyword, Long> keywordCountMap = queryFactory.select(reviewKeyword.keyword, reviewKeyword.keyword.count())
                    .from(reviewKeyword)
                    .where(reviewKeyword.review.product.productId.eq(productId))
                    .groupBy(reviewKeyword.keyword)
                    .fetch()
                    .stream()
                    .collect(Collectors.toMap(tuple -> tuple.get(reviewKeyword.keyword), tuple -> tuple.get(reviewKeyword.keyword.count())));

            for (Keyword keyword : Keyword.values()) {
                keywordCountMap.putIfAbsent(keyword, 0L);
            }

            result.setKeywordList(keywordCountMap);
        }

        return result;
    }


    @Override
    public ProductResponseDTO.ProductFindAllDTO findAllByTOP3() {
        List<ProductResponseDTO.ProductFindOneDTO> result = queryFactory.select(Projections.constructor(ProductResponseDTO.ProductFindOneDTO.class,
                        product.productId,
                        product.productName,
                        product.productMl,
                        product.productCategory,
                        product.thumbnail
                ))
                .from(product)
                .orderBy(product.views.desc())
                .limit(3)
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
        return new ProductResponseDTO.ProductFindAllDTO(result);
    }

    @Override
    public ProductResponseDTO.ProductFindAllDTO findAllBySweetener() {
        List<ProductResponseDTO.ProductFindOneDTO> result = queryFactory.select(Projections.constructor(ProductResponseDTO.ProductFindOneDTO.class,
                        product.productId,
                        product.productName,
                        product.productMl,
                        product.productCategory,
                        product.thumbnail
                ))
                .from(product)
                .leftJoin(productSweetener).on(productSweetener.product.productId.eq(product.productId))
                .leftJoin(productSweetener.sweetener, sweetener)
                .groupBy(product.productId)
                .orderBy(sweetener.count().asc())
                .limit(10)
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
        return new ProductResponseDTO.ProductFindAllDTO(result);
    }


    @Override
    public Page<ProductResponseDTO.ProductFindOneDTO> findAllByFilterWithPageable(ProductRequestDTO.ProductFilterDTO productFilterDTO, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (productFilterDTO.getKeyWord() != null && !productFilterDTO.getKeyWord() .isEmpty()) {
            builder.and(product.productName.containsIgnoreCase(productFilterDTO.getKeyWord() ));
        }

        if (productFilterDTO.getProductCategory() != null) {
            builder.and(product.productCategory.eq(productFilterDTO.getProductCategory()));
        }

        List<ProductResponseDTO.ProductFindOneDTO> result = queryFactory.select(Projections.constructor(ProductResponseDTO.ProductFindOneDTO.class,
                        product.productId,
                        product.productName,
                        product.productMl,
                        product.productCategory,
                        product.thumbnail
                ))
                .from(product)
                .where(builder)
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

            for (Keyword keywordEnum : Keyword.values()) {
                keywordCountMap.putIfAbsent(keywordEnum, 0L);
            }

            productDTO.setKeywordList(keywordCountMap);
        });

        long total = queryFactory.selectFrom(product)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(result, pageable, total);
    }
}

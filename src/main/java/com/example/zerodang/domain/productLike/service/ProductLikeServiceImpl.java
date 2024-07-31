package com.example.zerodang.domain.productLike.service;

import com.example.zerodang.domain.article.entity.Article;
import com.example.zerodang.domain.article.service.ArticleService;
import com.example.zerodang.domain.articleLike.entity.ArticleLike;
import com.example.zerodang.domain.articleLike.repository.ArticleLikeRepository;
import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.product.service.ProductService;
import com.example.zerodang.domain.productLike.entity.ProductLike;
import com.example.zerodang.domain.productLike.repository.ProductLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductLikeServiceImpl implements ProductLikeService {
    private final ProductLikeRepository productLikeRepository;
    private final ProductService productService;
    @Override
    @Transactional
    public void toggleByProductId(Long productId, Long userId) {
        Product findProduct = productService.getProduct_id(productId);
        Optional<ProductLike> optionalProductLike = productLikeRepository.findByProductAndUserId(findProduct, userId);

        if (optionalProductLike.isPresent()) {
            // 이미 존재하면 좋아요 취소
            productLikeRepository.deleteById(optionalProductLike.get().getProductLikeId());
            findProduct.minusLikes();
        } else {
            // 존재하지 않으면 좋아요 추가
            ProductLike newLike = ProductLike.builder()
                    .product(findProduct)
                    .userId(userId)
                    .build();
            productLikeRepository.save(newLike);
            findProduct.plusLikes();
        }
    }
}

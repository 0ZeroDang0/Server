package com.example.zerodang.domain.product.service;

import com.example.zerodang.domain.article.entity.Article;
import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.product.entity.ProductCategory;
import com.example.zerodang.domain.product.repository.ProductRepository;
import com.example.zerodang.global.exception.ErrorCode;
import com.example.zerodang.global.exception.article.ArticleNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public ProductResponseDTO.ProductFindAllDTO findAllByPopularity() {
        return null;
    }

    @Override
    public ProductResponseDTO.ProductFindAllDTO findAllByTOP3() {
        return null;
    }

    @Override
    public ProductResponseDTO.ProductFindAllDTO findAllBySweetener() {
        return null;
    }

    @Override
    public Page<ProductResponseDTO.ProductFindOneDTO> findAllByCategory(ProductCategory productCategory, Pageable pageable) {
        return productRepository.findAllByCategoryWithPageable(productCategory, pageable);
    }

    @Override
    public ProductResponseDTO.ProductDetailDTO findDetailByProductId(Long productId) {
        getProduct_id(productId);
        return productRepository.findDetailByProductId(productId);
    }

    private Product getProduct_id(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(!optionalProduct.isPresent()) {
            throw new ArticleNotFoundException(ErrorCode.NOT_FOUND_PRODUCT);
        }
        return optionalProduct.get();
    }
}

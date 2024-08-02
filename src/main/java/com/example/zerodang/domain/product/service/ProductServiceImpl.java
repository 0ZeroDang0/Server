package com.example.zerodang.domain.product.service;

import com.example.zerodang.domain.product.dto.request.ProductRequestDTO;
import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.product.entity.ProductCategory;
import com.example.zerodang.domain.product.repository.ProductRepository;
import com.example.zerodang.global.exception.ErrorCode;
import com.example.zerodang.global.exception.product.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return productRepository.findAllByTOP3();
    }

    @Override
    public ProductResponseDTO.ProductFindAllDTO findAllBySweetener() {
        return productRepository.findAllBySweetener();
    }

    @Override
    public Page<ProductResponseDTO.ProductFindOneDTO> findAllByCategory(ProductCategory productCategory, Pageable pageable) {
        return productRepository.findAllByCategoryWithPageable(productCategory, pageable);
    }

    @Override
    @Transactional
    public ProductResponseDTO.ProductDetailDTO findDetailByProductId(Long productId) {
        getProduct_id(productId).plusViews();
        return productRepository.findDetailByProductId(productId);
    }

    @Override
    public Page<ProductResponseDTO.ProductFindOneDTO> findAllByFilter(ProductRequestDTO.ProductFilterDTO productFilterDTO, Pageable pageable) {
        return productRepository.findAllByFilterWithPageable(productFilterDTO ,pageable);
    }

    @Override
    public Product getProduct_id(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.NOT_FOUND_PRODUCT));
    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 00시에 실행
    @Transactional
    public void resetProductViews() {
        productRepository.resetAllProductViews();
    }
}

package com.example.zerodang.domain.product.service;

import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import com.example.zerodang.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ProductResponseDTO.ProductFindAllDTO findAllByNewProduct() {
        return null;
    }

    @Override
    public ProductResponseDTO.ProductFindAllDTO findAllByCategory(Pageable pageable) {
        return null;
    }

    @Override
    public ProductResponseDTO.ProductDetailDTO findOneDetail(Long productId) {
        return null;
    }
}

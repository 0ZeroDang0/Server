package com.example.zerodang.domain.productAnalyze.service;

import com.example.zerodang.domain.product.service.ProductService;
import com.example.zerodang.domain.productAnalyze.dto.response.ProductAnalyzeResponseDTO;
import com.example.zerodang.domain.productAnalyze.entity.ProductAnalyze;
import com.example.zerodang.domain.productAnalyze.mapper.ProductAnalyzeMapper;
import com.example.zerodang.domain.productAnalyze.repository.ProductAnalyzeRepository;
import com.example.zerodang.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductAnalyzeServiceImpl implements ProductAnalyzeService {
    private final ProductAnalyzeRepository productAnalyzeRepository;
    private final ProductService productService;
    private final UserService userService;
    private final ProductAnalyzeMapper productAnalyzeMapper;

    @Override
    @Transactional
    public ProductAnalyzeResponseDTO.ProductAnalyzeSaveDTO cart(Long productId, Long userId) {
        ProductAnalyze productAnalyze = productAnalyzeMapper.toProductAnalyzeEntity(productService.getProduct_id(productId), userService.getUser_Id(userId));
        return productAnalyzeMapper.toProductAnalyzeSaveResDTO(productAnalyze);
    }
}

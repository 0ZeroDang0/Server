package com.example.zerodang.domain.productAnalyze.service;

import com.example.zerodang.domain.productAnalyze.dto.response.ProductAnalyzeResponseDTO;

public interface ProductAnalyzeService {
    ProductAnalyzeResponseDTO.ProductAnalyzeSaveDTO cart(Long productId, Long userId);
}

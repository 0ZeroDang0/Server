package com.example.zerodang.domain.productAnalyze.service;

import com.example.zerodang.domain.productAnalyze.dto.request.ProductAnalyzeRequestDTO;
import com.example.zerodang.domain.productAnalyze.dto.response.ProductAnalyzeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductAnalyzeService {
    ProductAnalyzeResponseDTO.ProductAnalyzeSaveDTO cart(Long productId, Long userId);
    Page<ProductAnalyzeResponseDTO.ProductAnalyzeFindOneDTO> findAllByUserId(Long userId, Pageable pageable);
    void delete(ProductAnalyzeRequestDTO.ProductAnalyzeDeleteDTO productAnalyzeDeleteDTO, Long userId);
}

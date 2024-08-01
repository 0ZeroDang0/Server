package com.example.zerodang.domain.productAnalyze.repository;

import com.example.zerodang.domain.productAnalyze.dto.response.ProductAnalyzeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductAnalyzeRepositoryCustom {
    Page<ProductAnalyzeResponseDTO.ProductAnalyzeFindOneDTO> findAllByUserIdWithPageable(Long userId, Pageable pageable);
}

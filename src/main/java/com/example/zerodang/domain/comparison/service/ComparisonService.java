package com.example.zerodang.domain.comparison.service;

import com.example.zerodang.domain.comparison.dto.response.ComparisonResponseDTO;

public interface ComparisonService {
    void comparison(Long product1, Long product2);
    ComparisonResponseDTO.ComparisonTOP3DTO findAllByTOP3();
}

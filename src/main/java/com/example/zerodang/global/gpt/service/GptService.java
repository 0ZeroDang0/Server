package com.example.zerodang.global.gpt.service;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.global.gpt.dto.GPTResponseDTO;
import reactor.core.publisher.Mono;

public interface GptService {
    Mono<String> analyzeWithGPT(Product product1, Product product2);

}

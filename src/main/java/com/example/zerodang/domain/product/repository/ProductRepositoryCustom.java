package com.example.zerodang.domain.product.repository;

import com.example.zerodang.domain.article.dto.response.ArticleResponseDTO;
import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<ProductResponseDTO.ProductFindOneDTO> findAllWithPageable(Pageable pageable);
}

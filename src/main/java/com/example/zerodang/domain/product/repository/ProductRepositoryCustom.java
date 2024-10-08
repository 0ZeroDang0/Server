package com.example.zerodang.domain.product.repository;

import com.example.zerodang.domain.product.dto.request.ProductRequestDTO;
import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import com.example.zerodang.domain.product.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<ProductResponseDTO.ProductFindOneDTO> findAllByCategoryWithPageable(ProductCategory productCategory, Pageable pageable);
    ProductResponseDTO.ProductDetailDTO findDetailByProductId(Long productId);
    ProductResponseDTO.ProductFindAllDTO findAllByTOP3();
    ProductResponseDTO.ProductFindAllDTO findAllBySweetener();
    Page<ProductResponseDTO.ProductFindOneDTO> findAllByFilterWithPageable(ProductRequestDTO.ProductFilterDTO productFilterDTO, Pageable pageable);
}

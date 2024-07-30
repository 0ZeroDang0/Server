package com.example.zerodang.domain.product.service;

import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import com.example.zerodang.domain.product.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    /** 실시간 최다 비교 상품 조회 **/
    ProductResponseDTO.ProductFindAllDTO findAllByPopularity();
    /** 실시간 TOP3 조회 **/
    ProductResponseDTO.ProductFindAllDTO findAllByTOP3();
    /** 인공 감미료 적은 수 조회 **/
    ProductResponseDTO.ProductFindAllDTO findAllBySweetener();
    /** 카테고리 별 조회 **/
    Page<ProductResponseDTO.ProductFindOneDTO> findAllByCategory(ProductCategory productCategory, Pageable pageable);
    /** 상품 상제 조회 **/
    ProductResponseDTO.ProductDetailDTO findDetailByProductId(Long productId);
}

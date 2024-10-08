package com.example.zerodang.domain.product.service;

import com.example.zerodang.domain.product.dto.request.ProductRequestDTO;
import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.product.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    /** 실시간 TOP3 조회 **/
    ProductResponseDTO.ProductFindAllDTO findAllByTOP3();
    /** 인공 감미료 적은 수 조회 **/
    ProductResponseDTO.ProductFindAllDTO findAllBySweetener();
    /** 카테고리 별 조회 **/
    Page<ProductResponseDTO.ProductFindOneDTO> findAllByCategory(ProductCategory productCategory, Pageable pageable);
    /** 상품 상제 조회 **/
    ProductResponseDTO.ProductDetailDTO findDetailByProductId(Long productId);
    /** 상품 필터 조회 **/
    Page<ProductResponseDTO.ProductFindOneDTO> findAllByFilter(ProductRequestDTO.ProductFilterDTO productFilterDTO, Pageable pageable) ;
    Product getProduct_id(Long productId);
}

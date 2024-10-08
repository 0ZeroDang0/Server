package com.example.zerodang.domain.product.controller;

import com.example.zerodang.domain.product.dto.request.ProductRequestDTO;
import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import com.example.zerodang.domain.product.entity.ProductCategory;
import com.example.zerodang.domain.product.service.ProductService;
import com.example.zerodang.global.response.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member/product")
@Tag(name = "Product", description = "상품 관련 API")
@RequiredArgsConstructor
@Slf4j
public class ProductApiController {
    private final ProductService productService;

    @GetMapping("/findAllByCategory")
    @Operation(summary = "상품 전체 조회", description = "상품 전체를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ProductResponseDTO.ProductFindOneDTO.class)))
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> findAllByCategory(@RequestParam(value = "productCategory", required = false) ProductCategory productCategory, Pageable pageable) {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), productService.findAllByCategory(productCategory, pageable)));
    }

    @GetMapping("/findDetail/{productId}")
    @Operation(summary = "상품 자세히 조회", description = "상품 자세히 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ProductResponseDTO.ProductDetailDTO.class)))
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> findDetailByProductId(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), productService.findDetailByProductId(productId)));
    }

    @GetMapping("/findAllByTOP3")
    @Operation(summary = "상품 TOP3 조회", description = "상품 TOP3 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ProductResponseDTO.ProductFindAllDTO.class)))
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> findAllByTOP3() {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), productService.findAllByTOP3()));
    }

    @GetMapping("/findAllBySweetener")
    @Operation(summary = "상품 인공감미료 최저순 조회", description = "상품 인공감미료 최저순 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ProductResponseDTO.ProductFindAllDTO.class)))
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> findAllBySweetener() {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), productService.findAllBySweetener()));
    }

    @GetMapping("/findAllByFilter")
    @Operation(summary = "상품 키워드 + 카테고리 조회", description = "상품 키워드 + 카테고리 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ProductResponseDTO.ProductFindOneDTO.class)))
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> findAllByFilter(@RequestParam(value = "keyWord", required = false) String keyWord,
                                             @RequestParam(value = "productCategory", required = false) ProductCategory productCategory,
                                             Pageable pageable
                                             ) {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), productService.findAllByFilter(getProductFIlterDTO(keyWord, productCategory), pageable)));
    }

    private static ProductRequestDTO.ProductFilterDTO getProductFIlterDTO(String keyWord, ProductCategory productCategory) {
        return ProductRequestDTO.ProductFilterDTO.builder()
                .keyWord(keyWord)
                .productCategory(productCategory)
                .build();
    }
}

package com.example.zerodang.domain.productAnalyze.controller;

import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import com.example.zerodang.domain.product.entity.ProductCategory;
import com.example.zerodang.domain.productAnalyze.dto.request.ProductAnalyzeRequestDTO;
import com.example.zerodang.domain.productAnalyze.dto.response.ProductAnalyzeResponseDTO;
import com.example.zerodang.domain.productAnalyze.entity.ProductAnalyze;
import com.example.zerodang.domain.productAnalyze.service.ProductAnalyzeService;
import com.example.zerodang.domain.user.dto.request.UserRequestDTO;
import com.example.zerodang.domain.user.dto.response.UserResponseDTO;
import com.example.zerodang.global.response.CustomResponse;
import com.example.zerodang.global.security.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member/productAnalyze")
@Tag(name = "ProductAnalyze", description = "상품 비교 관련 API")
@RequiredArgsConstructor
@Slf4j
public class ProductAnalyzeApiController {
    private final ProductAnalyzeService productAnalyzeService;

    @PostMapping("/cart/{productId}")
    @Operation(summary = "담기 API", description = "담기 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ProductAnalyzeResponseDTO.ProductAnalyzeSaveDTO.class)))
    })
    public ResponseEntity<?> cart(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), productAnalyzeService.cart(productId, SecurityUtil.getCurrentId())));

    }
    @GetMapping("/findAllByUserId")
    @Operation(summary = "담기 전체 조회", description = "담기 전체를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ProductResponseDTO.ProductFindOneDTO.class)))
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> findAllByUserId(Pageable pageable) {

        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), productAnalyzeService.findAllByUserId(SecurityUtil.getCurrentId(), pageable)));
    }

    @PostMapping("/delete")
    @Operation(summary = "담은 상품 삭제 API", description = "담은 상품 삭제 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SUCCESS")
    })
    public ResponseEntity<?> delete(@RequestBody ProductAnalyzeRequestDTO.ProductAnalyzeDeleteDTO productAnalyzeDeleteDTO) {
        productAnalyzeService.delete(productAnalyzeDeleteDTO, SecurityUtil.getCurrentId());
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value()));
    }

    @PostMapping("/result")
    @Operation(summary = "상품 비교 결과 API", description = "두 상품의 비교 결과를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ProductAnalyzeResponseDTO.ProductAnalyzeResultDTO.class)))
//            @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    })
    public ResponseEntity<?> result(@RequestBody ProductAnalyzeRequestDTO.ProductAnalyzeComparisonDTO productAnalyzeComparisonDTO) {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.OK.value(), productAnalyzeService.result(productAnalyzeComparisonDTO)));
    }
}

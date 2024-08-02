package com.example.zerodang.domain.review.controller;

import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import com.example.zerodang.domain.product.entity.ProductCategory;
import com.example.zerodang.domain.review.dto.request.ReviewRequestDTO;
import com.example.zerodang.domain.review.dto.response.ReviewResponseDTO;
import com.example.zerodang.domain.review.service.ReviewService;
import com.example.zerodang.global.response.CustomResponse;
import com.example.zerodang.global.security.util.SecurityUtil;
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
@RequestMapping("/api/v1/member/review")
@Tag(name = "Review", description = "리뷰 관련 API")
@RequiredArgsConstructor
@Slf4j
public class ReviewApiController {
    private final ReviewService reviewService;

    @PostMapping("/save")
    @Operation(summary = "리뷰 작성", description = "리뷰를 작성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ReviewResponseDTO.ReviewDetailDTO.class)))
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> save(@RequestBody ReviewRequestDTO.ReviewSaveDTO reviewSaveDTO) {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), reviewService.save(reviewSaveDTO, SecurityUtil.getCurrentId())));
    }

    @GetMapping("/findAllByProductId/{productId}")
    @Operation(summary = "리뷰 전체 조회", description = "리뷰 전체를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ReviewResponseDTO.ReviewDetailDTO.class)))
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> findAllByProductId(@PathVariable("productId") Long productId, Pageable pageable) {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), reviewService.findAllByProductId(productId, pageable)));
    }

    @GetMapping("/findCountByProductId/{productId}")
    @Operation(summary = "리뷰 카운트 조회", description = "리뷰 카운트 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ReviewResponseDTO.ReviewFindCountDTO.class)))
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> findCountByProductId(@PathVariable("productId") Long productId, Pageable pageable) {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), reviewService.findCountByProductId(productId)));
    }
}

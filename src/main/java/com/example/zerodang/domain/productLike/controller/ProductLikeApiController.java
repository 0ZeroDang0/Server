package com.example.zerodang.domain.productLike.controller;

import com.example.zerodang.domain.productLike.service.ProductLikeService;
import com.example.zerodang.global.response.CustomResponse;
import com.example.zerodang.global.security.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member/productLike")
@Tag(name = "ProductLike", description = "상요 좋아 관련 API")
@RequiredArgsConstructor
@Slf4j
public class ProductLikeApiController {
    private final ProductLikeService productLikeService;
    @PostMapping("/toggle/{productId}")
    @Operation(summary = "상품 좋아요 토글", description = "상품 좋아요 토글 입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS")
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> toggleByProductId(@PathVariable("productId") Long productId) {
        productLikeService.toggleByProductId(productId, SecurityUtil.getCurrentId());
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value()));
    }
}

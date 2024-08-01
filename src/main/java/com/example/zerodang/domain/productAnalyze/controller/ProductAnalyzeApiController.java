package com.example.zerodang.domain.productAnalyze.controller;

import com.example.zerodang.domain.productAnalyze.dto.request.ProductAnalyzeRequestDTO;
import com.example.zerodang.domain.productAnalyze.service.ProductAnalyzeService;
import com.example.zerodang.domain.user.dto.request.UserRequestDTO;
import com.example.zerodang.domain.user.dto.response.UserResponseDTO;
import com.example.zerodang.global.response.CustomResponse;
import com.example.zerodang.global.security.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/cart")
    @Operation(summary = "회원가입 API", description = "회원가입 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = UserResponseDTO.UserJoinDTO.class)))
    })
    public ResponseEntity<?> cart(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), productAnalyzeService.cart(productId, SecurityUtil.getCurrentId())));
    }
}

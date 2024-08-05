package com.example.zerodang.domain.comparison.controller;

import com.example.zerodang.domain.comparison.dto.response.ComparisonResponseDTO;
import com.example.zerodang.domain.comparison.service.ComparisonService;
import com.example.zerodang.domain.product.dto.response.ProductResponseDTO;
import com.example.zerodang.global.response.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member/comparison")
@Tag(name = "Comparison", description = "상품 비교 관련 API")
@RequiredArgsConstructor
@Slf4j
public class ComparisonApiController {
    private final ComparisonService comparisonService;

    @GetMapping("/findAllByTOP3")
    @Operation(summary = "최다 비교 TOP3 조회", description = "최다 비교 TOP3 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = ComparisonResponseDTO.ComparisonTOP3DTO.class)))
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> findAllByTOP3() {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), comparisonService.findAllByTOP3()));
    }
}

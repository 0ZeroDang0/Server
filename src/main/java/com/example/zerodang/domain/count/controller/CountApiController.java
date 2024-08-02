package com.example.zerodang.domain.count.controller;

import com.example.zerodang.domain.count.dto.response.VisitorStatsResponseDTO;
import com.example.zerodang.domain.count.service.CountService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member/count")
@Tag(name = "Count", description = "카운트 관련 API")
@RequiredArgsConstructor
@Slf4j
public class CountApiController {
    private final CountService visitorStatsService;

    @PostMapping("/recordVisit")
    @Operation(summary = "방문자 수 기록", description = "방문자 수를 기록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS")
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> recordVisit() {
        visitorStatsService.recordVisit();
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value()));
    }

    @GetMapping("/findVisitorStatsCount")
    @Operation(summary = "방문자 수 조회", description = "방문자 수를 조 합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = VisitorStatsResponseDTO.VisitorStatsCountDTO.class)))
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> findVisitorStatsCount() {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), visitorStatsService.findCount()));
    }
}

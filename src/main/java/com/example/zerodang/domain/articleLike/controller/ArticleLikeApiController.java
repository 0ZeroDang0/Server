package com.example.zerodang.domain.articleLike.controller;

import com.example.zerodang.domain.article.dto.response.ArticleResponseDTO;
import com.example.zerodang.domain.articleLike.service.ArticleLikeService;
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

import java.security.Security;

@RestController
@RequestMapping("/api/v1/member/articleLike")
@Tag(name = "ArticleLike", description = "아티클 좋아요 관련 API")
@RequiredArgsConstructor
@Slf4j
public class ArticleLikeApiController {
    private final ArticleLikeService articleLikeService;
    @PostMapping("/toggle/{articleId}")
    @Operation(summary = "아티클 좋아요 토글", description = "아티클 좋아요 토글 입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "SUCCESS")
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> toggleByArticleId(@PathVariable("articleId") Long articleId) {
        articleLikeService.toggleByArticleId(articleId, SecurityUtil.getCurrentId());
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value()));
    }
}

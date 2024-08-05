package com.example.zerodang.domain.kakao.controller;

import com.example.zerodang.domain.kakao.service.KakaoService;
import com.example.zerodang.global.response.CustomResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member/kakao")
@Tag(name = "Kakao", description = "카카오 관련 API")
@RequiredArgsConstructor
@Slf4j
public class KakaoApiController {
    private final KakaoService kakaoService;
    /** 카카오 로그인 **/
    @GetMapping("/kakao")
    public ResponseEntity<?> kakao(@RequestParam(value = "code") String code) {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), kakaoService.kakao(code)));
    }
}

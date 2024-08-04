package com.example.zerodang.domain.kakao.controller;

import com.example.zerodang.domain.kakao.service.KakaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Kakao", description = "카카오 관련 API")
@RequiredArgsConstructor
@Slf4j
public class KakaoApiController {
    private final KakaoService kakaoService;
    //web 버전
//    @ResponseBody
//    @GetMapping("/login/oauth/kakao")
//    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletRequest request){
//        try{
//            // 현재 도메인 확인
//            String currentDomain = request.getServerName();
//            return ResponseEntity.ok(kakaoService.kakaoLogin(code, currentDomain));
//        } catch (NoSuchElementException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Item Not Found");
//        }
//    }
}

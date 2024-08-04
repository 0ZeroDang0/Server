package com.example.zerodang.domain.kakao.service;

import com.example.zerodang.domain.user.repository.UserRepository;
import com.example.zerodang.global.security.jwt.JwtDto;
import com.example.zerodang.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class KakaoServiceImpl implements KakaoService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    /** Web 버전 카카오 로그인 **/
//    public JwtDto kakaoLogin(String code, String currentDomain) {
//        //0. 동적으로 redirect URI 선택
//        String redirectUri=selectRedirectUri(currentDomain);
//
//        // 1. "인가 코드"로 "액세스 토큰" 요청
//        String accessToken = getAccessToken(code, redirectUri);
//
//        // 2. 토큰으로 카카오 API 호출
//        HashMap<String, Object> userInfo= getKakaoUserInfo(accessToken);
//
//        //3. 카카오ID로 회원가입 & 로그인 처리
//        JwtDto JwtDto= kakaoUserLogin(userInfo);
//
//        return JwtDto;
//    }
}

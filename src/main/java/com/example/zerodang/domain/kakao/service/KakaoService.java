package com.example.zerodang.domain.kakao.service;

import com.example.zerodang.domain.user.entity.User;
import com.example.zerodang.domain.user.entity.UserStatus;
import com.example.zerodang.domain.user.repository.UserRepository;
import com.example.zerodang.global.security.jwt.JwtDto;
import com.example.zerodang.global.security.jwt.JwtProvider;
import com.example.zerodang.global.security.jwt.ZeroDangRole;
import com.example.zerodang.global.security.oauth.provider.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class KakaoService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoTokenUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUrl;
    @Transactional
    public JwtDto kakao(String code) {
        String accessToken = getKakaoAccessToken(code);
        Map<String, Object> userInfo = getKakaoUserInfo(accessToken);

        String email = userInfo.get("email").toString();
        User user = userRepository.findByUserEmail(email).orElseGet(() -> createUser(userInfo));

        return jwtProvider.createJwtDto(user.getUserId(), user.getZeroDangRole());
    }

    private String getKakaoAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(kakaoTokenUrl, request, Map.class);

        return response.getBody().get("access_token").toString();
    }

    private Map<String, Object> getKakaoUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(kakaoUserInfoUrl, HttpMethod.GET, request, Map.class);

        return response.getBody();
    }

    private User createUser(Map<String, Object> userInfo) {
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(userInfo);
        User user = User.builder()
                .userEmail(kakaoUserInfo.getEmail())
                .userPassword("kakao")
                .userName(kakaoUserInfo.getName())
                .userImg(kakaoUserInfo.getImg())
                .zeroDangRole(ZeroDangRole.USER)
                .userStatus(UserStatus.ACTIVE)
                .lastLogin(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }
}

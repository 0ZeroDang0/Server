package com.example.zerodang.global.security.oauth;

import com.example.zerodang.domain.user.entity.User;
import com.example.zerodang.domain.user.entity.UserStatus;
import com.example.zerodang.domain.user.repository.UserRepository;
import com.example.zerodang.global.security.jwt.ZeroDangRole;
import com.example.zerodang.global.security.oauth.provider.KakaoUserInfo;
import com.example.zerodang.global.security.oauth.provider.Oauth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("[loadUser] 입장");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        log.info("[processOAuth2User] 입장");
        Oauth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            log.info("[PrincipalOauth2UserService] 카카오 소셜 로그인 시도");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }
        else {
            log.info("[PrincipalOauth2UserService] 제공하지 않는 소셜 로그인 입니다.");
        }

        String userInfoEmail = oAuth2UserInfo.getEmail();
        String userInfoName = oAuth2UserInfo.getName();
        String userInfoImg = oAuth2UserInfo.getImg();

        log.info("userInfoEmail: {}", userInfoEmail);
        log.info("userInfoName: {}", userInfoName);
        log.info("userInfoImg: {}", userInfoImg);

        User user = userRepository.findByUserEmail(userInfoEmail)
                .orElse(null);

        if (user != null) {
            log.info("로그인을 이미 했음, 자동 회원가입이 되어있다.");
        }  else {
            user = User.builder()
                    .userEmail(userInfoEmail)
                    .userPassword("kakao")
                    .userName(userInfoName)
                    .userImg(userInfoImg)
                    .zeroDangRole(ZeroDangRole.USER)
                    .userStatus(UserStatus.ACTIVE)
                    .lastLogin(LocalDateTime.now())
                    .build();
            userRepository.save(user);
        }

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("userId", user.getUserId());
        attributes.put("email", user.getUserEmail());

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getZeroDangRole().name());

        return new DefaultOAuth2User(Collections.singletonList(authority), attributes, "email");
    }
}
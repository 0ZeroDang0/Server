package com.example.zerodang.global.security.jwt;

import com.example.zerodang.domain.user.repository.UserRepository;
import com.example.zerodang.domain.user.service.UserService;
import com.example.zerodang.global.exception.secure.SecureException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.zerodang.global.security.jwt.ZeroDangRole.PARTNER;
import static com.example.zerodang.global.security.jwt.ZeroDangRole.USER;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(JwtProvider.class);

    private final static String JWT_TOKEN_ID = "PETAROUND_API";
    private final static String ID = "id";

    private SecretKey memberAccessToken;
    private SecretKey memberRefreshToken;
    private SecretKey partnerAccessToken;
    private SecretKey partnerRefreshToken;

    public void oauthCreateJwtDto(HttpServletResponse response, String email) throws IOException {
        // email을 userId로 변환하는 로직이 필요합니다.
        // 여기서는 가정적으로 이메일에서 userId를 가져온다고 가정합니다.
        Long userId = userRepository.findByUserEmail(email).get().getUserId();
        String accessToken;
        String refreshToken;
        Date accessTokenExpiredDate = this.computeExpiredDate(JwtType.ACCESS);
        Date refreshTokenExpiredDate = this.computeExpiredDate(JwtType.REFRESH);

        accessToken = createUserAccessToken(userId, accessTokenExpiredDate);
        refreshToken = createUserRefreshToken(userId, refreshTokenExpiredDate);

        response.addHeader("Authorization", "Bearer " + accessToken);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);
        response.setContentType("application/json");

        new ObjectMapper().writeValue(response.getOutputStream(), tokenMap);
    }
//    public void oauthCreateJwtDto(HttpServletResponse response, UserDetails userDetails) throws IOException {
//        String accessToken;
//        String refreshToken;
//        Date accessTokenExpiredDate = this.computeExpiredDate(JwtType.ACCESS);
//        Date refreshTokenExpiredDate = this.computeExpiredDate(JwtType.REFRESH);
//
//        accessToken = createUserAccessToken(Long.parseLong(userDetails.getUsername()), accessTokenExpiredDate);
//        refreshToken = createUserRefreshToken(Long.parseLong(userDetails.getUsername()), refreshTokenExpiredDate);
//
//        response.addHeader("Authorization", "Bearer " + accessToken);
//        Map<String, String> tokenMap = new HashMap<>();
//        tokenMap.put("accessToken", accessToken);
//        tokenMap.put("refreshToken", refreshToken);
//        response.setContentType("application/json");
//
//        new ObjectMapper().writeValue(response.getOutputStream(), tokenMap);
//    }


    @PostConstruct
    public void init() {
        byte[] memberAccessBytes = Base64.getEncoder().encode(
                jwtProperties.memberAccessSecretKey().getBytes(StandardCharsets.UTF_8)
        );
        byte[] memberRefreshBytes = Base64.getEncoder().encode(
                jwtProperties.memberRefreshSecretKey().getBytes(StandardCharsets.UTF_8)
        );

        byte[] partnerAccessBytes = Base64.getEncoder().encode(
                jwtProperties.partnerAccessSecretKey().getBytes(StandardCharsets.UTF_8)
        );
        byte[] partnerRefreshBytes = Base64.getEncoder().encode(
                jwtProperties.partnerRefreshSecretKey().getBytes(StandardCharsets.UTF_8)
        );

        this.memberAccessToken = Keys.hmacShaKeyFor(memberAccessBytes);
        this.memberRefreshToken = Keys.hmacShaKeyFor(memberRefreshBytes);
        this.partnerAccessToken = Keys.hmacShaKeyFor(partnerAccessBytes);
        this.partnerRefreshToken = Keys.hmacShaKeyFor(partnerRefreshBytes);
    }

    public JwtDto createJwtDto(Long userId, ZeroDangRole subject) {
        String accessToken;
        String refreshToken;
        Date accessTokenExpiredDate = this.computeExpiredDate(JwtType.ACCESS);
        Date refreshTokenExpiredDate = this.computeExpiredDate(JwtType.REFRESH);

        switch (subject) {
            case USER -> {
                accessToken = createUserAccessToken(userId, accessTokenExpiredDate);
                refreshToken = createUserRefreshToken(userId, refreshTokenExpiredDate);
            }
            case PARTNER -> {
                accessToken = createPartnerAccessToken(userId, accessTokenExpiredDate);
                refreshToken = createPartnerRefreshToken(userId, refreshTokenExpiredDate);
            }
            default -> throw new SecureException.JwtCreateException("지원하지 않는 타입입니다 :: " + subject);
        }

        log.debug("{} {}의 토큰을 생성하였습니다.", subject, userId);

        return new JwtDto(
                userId,
                accessToken,
                refreshToken,
                accessTokenExpiredDate.getTime(),
                refreshTokenExpiredDate.getTime()
        );
    }

    private Date computeExpiredDate(JwtType type) {
        return Date.from(
                LocalDateTime.now()
                        .plusSeconds(
                                type == JwtType.ACCESS ?
                                        jwtProperties.accessExpiredSecond() :
                                        jwtProperties.refreshExpiredSecond()
                        )
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
    }

    public String createUserAccessToken(Long userId, Date expiredDate) {
        return this.createToken(userId, expiredDate, memberAccessToken, USER);
    }

    public String createUserRefreshToken(Long userId, Date expiredDate) {
        return this.createToken(userId, expiredDate, memberRefreshToken, USER);
    }

    public String createPartnerAccessToken(Long userId, Date expiredDate) {
        return this.createToken(userId, expiredDate, partnerAccessToken, PARTNER);
    }

    public String createPartnerRefreshToken(Long userId, Date expiredDate) {
        return this.createToken(userId, expiredDate, partnerRefreshToken, PARTNER);
    }

    private String createToken(
            Long userId,
            Date expiredDate,
            Key key,
            ZeroDangRole subject
    ) {
        log.debug("토큰 생성을 요청하였습니다 :: {} :: {} :: {} :: {}", userId, expiredDate, key, subject.name());

        return Jwts.builder().header().add(
                        Map.of(
                                "typ", "JWT",
                                "alg", key.getAlgorithm()
                        )
                ).and().claims(
                        Map.of(
                                ID, userId,
                                "role", subject.name()
                        )
                )
                .issuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .id(JWT_TOKEN_ID)
                .issuer(JWT_TOKEN_ID)
                .subject(subject.name())
                .expiration(expiredDate)
                .signWith(key).compact();
    }

    public Long getMemberId(String jwt) {
        return getUserIdByJwt(jwt, USER, JwtType.ACCESS);
    }

    public Long getPartnerId(String jwt) {
        return getUserIdByJwt(jwt, PARTNER, JwtType.ACCESS);
    }

    private Long getUserIdByJwt(String jwt, ZeroDangRole subject, JwtType type) {
        SecretKey key = this.getSecretKey(subject, type);
        Jwt<JwsHeader, Claims> token = this.parseToken(jwt, key, subject);
        Object object = token.getPayload().get(ID);

        if (object instanceof Number id) {
            return id.longValue();
        }

        throw new SecureException.JwtVerifyException("올바르지 않은 토큰입니다.");
    }

    private SecretKey getSecretKey(ZeroDangRole subject, JwtType type) {
        return switch (subject) {
            case USER -> switch (type) {
                case ACCESS -> memberAccessToken;
                case REFRESH -> memberRefreshToken;
            };
            case PARTNER -> switch (type) {
                case ACCESS -> partnerAccessToken;
                case REFRESH -> partnerRefreshToken;
            };
            default -> throw new SecureException.JwtSecretKeyException("올바르지 않은 타입으로 `SecretKey`를 요청하였습니다.");
        };
    }

    private Jwt<JwsHeader, Claims> parseToken(String jwt, SecretKey key, ZeroDangRole subject) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(key)
                    .requireId(JWT_TOKEN_ID)
                    .requireSubject(subject.name())
                    .requireIssuer(JWT_TOKEN_ID)
                    .build()
                    .parseSignedClaims(jwt);
        } catch (Exception e) {
            throw new SecureException.JwtVerifyException(e.getMessage(), e);
        }
    }

}

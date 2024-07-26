package com.example.zerodang.global.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String memberAccessSecretKey,
        String memberRefreshSecretKey,
        String partnerAccessSecretKey,
        String partnerRefreshSecretKey,
        long accessExpiredSecond,
        long refreshExpiredSecond
) {
}

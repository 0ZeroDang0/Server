package com.example.zerodang.global.security.jwt;

public record JwtDto(
        Long id,
        String accessToken,
        String refreshToken,
        Long accessExpiredTime,
        Long refreshExpiredTime
) {
}

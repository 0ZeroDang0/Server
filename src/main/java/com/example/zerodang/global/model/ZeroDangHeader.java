package com.example.zerodang.global.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ZeroDangHeader {
    AUTHORIZATION("Authorization", null),
    AUTHORIZATION_ROLE("Role", null);

    private final String key;
    private final String value;
}

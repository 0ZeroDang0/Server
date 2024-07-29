package com.example.zerodang.domain.user.dto.response;

import com.example.zerodang.domain.user.entity.UserStatus;
import lombok.*;

public class UserResponseDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class UserJoinDTO {
        private Long userId;
        private String userEmail;
        private String userName;
        private UserStatus userStatus;
    }
}

package com.example.zerodang.domain.user.dto.response;

import com.example.zerodang.domain.user.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserResponseDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserLoginDTO {
        private Long userId;
        private String userEmail;
        private String userName;
        private UserStatus userStatus;
        private LocalDateTime lastLogin;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserJoinDTO {
        private Long userId;
        private String userEmail;
        private String userName;
        private UserStatus userStatus;
    }
}

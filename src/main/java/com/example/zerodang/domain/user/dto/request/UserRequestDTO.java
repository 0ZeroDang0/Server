package com.example.zerodang.domain.user.dto.request;

import lombok.Data;

public class UserRequestDTO {
    @Data
    public static class UserLoginDTO {
        private String userEmail;
        private String userPassword;
    }

    @Data
    public static class UserJoinDTO {
        private String userEmail;
        private String userPassword;
        private String userName;
    }
}

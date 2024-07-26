package com.example.zerodang.global.exception.user;

import com.example.zerodang.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
public class UserInvalidPasswordException extends RuntimeException {
    private final ErrorCode errorCode;
    public UserInvalidPasswordException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
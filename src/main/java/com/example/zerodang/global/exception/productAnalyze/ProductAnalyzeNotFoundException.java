package com.example.zerodang.global.exception.productAnalyze;

import com.example.zerodang.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class ProductAnalyzeNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
    public ProductAnalyzeNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
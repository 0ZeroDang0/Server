package com.example.zerodang.global.exception;

import com.example.zerodang.global.exception.secure.SecureException;
import com.example.zerodang.global.exception.user.UserEmailDuplicationException;
import com.example.zerodang.global.exception.user.UserInvalidPasswordException;
import com.example.zerodang.global.exception.user.UserNotFoundException;
import com.example.zerodang.global.exception.user.UserProfileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionCustomAdvice {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> exceptionHandler(Exception ex) {
        log.debug(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse(
                        ErrorCode.INTERNAL_SERVER_ERROR.getStatus(),
                        ex.getMessage()));
    }

    @ExceptionHandler(UserEmailDuplicationException.class)
    public ResponseEntity<ApiErrorResponse> handleException(UserEmailDuplicationException ex) {
        log.debug(ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        ErrorCode.EMAIL_DUPLICATION_USER.getStatus(),
                        ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserInvalidPasswordException.class)
    public ResponseEntity<ApiErrorResponse> handleException(UserInvalidPasswordException ex) {
        log.debug(ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        ErrorCode.INVALID_PASSWORD_USER.getStatus(),
                        ex.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(UserNotFoundException ex) {
        log.debug(ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        ErrorCode.NOT_FOUND_USER.getStatus(),
                        ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserProfileNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(UserProfileNotFoundException ex) {
        log.debug(ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        ErrorCode.NOT_FOUND_USER_PROFILE.getStatus(),
                        ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SecureException.JwtCreateException.class)
    public ResponseEntity<ApiErrorResponse> handleException(SecureException.JwtCreateException ex) {
        log.debug(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse(
                        ErrorCode.INTERNAL_SERVER_ERROR.getStatus(),
                        "서버 에러가 발생하였습니다."));
    }

    @ExceptionHandler(SecureException.JwtVerifyException.class)
    public ResponseEntity<ApiErrorResponse> handleException(SecureException.JwtVerifyException ex) {
        log.debug(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiErrorResponse(
                        ErrorCode.WRONG_TOKEN.getStatus(),
                        "올바르지 않은 인증 토큰입니다"));
    }
}

package com.example.zerodang.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse<T> {
    private Integer code;
    private String message;
    private ResponseStatus status;
    private T data;

    public CustomResponse(Integer code, ResponseStatus status) {
        this.code = code;
        this.status = status;
    }

    public CustomResponse(Integer code, ResponseStatus status, T data) {
        this.code = code;
        this.status = status;
        this.data = data;
    }

    public static <T> CustomResponse<T> SUCCESS(Integer code) {
        return new CustomResponse<>(code, ResponseStatus.SUCCESS);
    }

    public static <T> CustomResponse<T> SUCCESS(Integer code, T data) {
        return new CustomResponse<>(code, ResponseStatus.SUCCESS, data);
    }

//    public static <T> CustomResponse<T> FAILURE(Integer code, String message) {
//        return new CustomResponse<>(code, ResponseStatus.FAIL, message);
//    }
//
//    public static <T> CustomResponse<T> ERROR(Integer code, String message) {
//        return new CustomResponse<>(code, ResponseStatus.ERROR, message);
//    }
}

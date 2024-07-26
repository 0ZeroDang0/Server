package com.example.zerodang.global.exception.secure;

public class SecureException {

    public static class JwtCreateException extends RuntimeException {
        public JwtCreateException(String message) {
            super(message);
        }
    }

    public static class JwtVerifyException extends RuntimeException {

        public JwtVerifyException(String message) {
            super(message);
        }

        public JwtVerifyException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }

    public static class JwtSecretKeyException extends RuntimeException {
        public JwtSecretKeyException(String message) {
            super(message);
        }
    }


}
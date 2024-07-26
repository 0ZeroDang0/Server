package com.example.zerodang.global.security;

import com.example.zerodang.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class SecurityExceptionHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String message = "{ \"message\": \"" + ErrorCode.WRONG_TOKEN.name() + "\" }";
        response.getWriter().print(message);
        response.setContentType(MediaType.APPLICATION_JSON.toString());
    }
}


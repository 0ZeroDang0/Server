package com.example.zerodang.global.security;

import com.example.zerodang.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class SecurityDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String message = "{ \"message\": \"" + ErrorCode.ACCESS_DENIED.name() + "\" }";
        response.getWriter().print(message);
        response.setContentType(MediaType.APPLICATION_JSON.toString());
    }
}

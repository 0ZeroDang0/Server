package com.example.zerodang.global.security;

import com.example.zerodang.domain.user.repository.UserRepository;
import com.example.zerodang.global.security.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@AllArgsConstructor
public class ZeroDangSecurityConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    @Override
    public void configure(HttpSecurity security) {
        security.addFilterAfter(
                new SecurityFilter(jwtProvider, userRepository),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}

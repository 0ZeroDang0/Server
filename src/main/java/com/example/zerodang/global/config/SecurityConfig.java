package com.example.zerodang.global.config;

import com.example.zerodang.domain.user.repository.UserRepository;
import com.example.zerodang.global.security.SecurityDeniedHandler;
import com.example.zerodang.global.security.SecurityExceptionHandler;
import com.example.zerodang.global.security.ZeroDangSecurityConfigurerAdapter;
import com.example.zerodang.global.security.jwt.JwtProvider;
import com.example.zerodang.global.security.jwt.ZeroDangRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final SecurityDeniedHandler securityDeniedHandler;
    private final SecurityExceptionHandler securityExceptionHandler;
    private final UserRepository userRepository;

    public SecurityConfig(@Autowired JwtProvider jwtProvider, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.securityDeniedHandler = new SecurityDeniedHandler();
        this.securityExceptionHandler = new SecurityExceptionHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(it -> {
                            it.authenticationEntryPoint(securityExceptionHandler);
                            it.accessDeniedHandler(securityDeniedHandler);
                        }
                )
                .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/member/auth/**").permitAll()
                                // 스웨거
                                .requestMatchers( "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers( "/api/v1/member/product/**").permitAll()
                                .requestMatchers( "/api/v1/member/article/**").permitAll()
                                .requestMatchers( HttpMethod.GET, "/api/v1/member/review/**").permitAll()
                                .requestMatchers( "/api/v1/member/productAnalyze/**").permitAll()
                                .requestMatchers( "/api/v1/member/count/**").permitAll()
                                // GET (User)
                                .requestMatchers(HttpMethod.GET, "/api/v1/**").hasRole(ZeroDangRole.USER.name())

                                // POST (User)
                                .requestMatchers(HttpMethod.POST, "/api/v1/**").hasRole(ZeroDangRole.USER.name())

                                // DELETE (User)
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasRole(ZeroDangRole.USER.name())

                                // PUT (User)
                                .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasRole(ZeroDangRole.USER.name())

                                // PATCH (User)
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/**").hasRole(ZeroDangRole.USER.name())
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/**").denyAll()
                )
                .with(
                        new ZeroDangSecurityConfigurerAdapter(jwtProvider, userRepository),
                        it -> {}
                )
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

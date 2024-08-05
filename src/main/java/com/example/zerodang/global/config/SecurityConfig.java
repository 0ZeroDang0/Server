package com.example.zerodang.global.config;

import com.example.zerodang.domain.user.repository.UserRepository;
import com.example.zerodang.global.security.SecurityDeniedHandler;
import com.example.zerodang.global.security.SecurityExceptionHandler;
import com.example.zerodang.global.security.ZeroDangSecurityConfigurerAdapter;
import com.example.zerodang.global.security.jwt.JwtProvider;
import com.example.zerodang.global.security.jwt.ZeroDangRole;
import com.example.zerodang.global.security.oauth.PrincipalOauth2UserService;
import com.example.zerodang.global.security.oauth.handler.Oauth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final SecurityDeniedHandler securityDeniedHandler;
    private final SecurityExceptionHandler securityExceptionHandler;
    private final UserRepository userRepository;
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;

    public SecurityConfig(@Autowired JwtProvider jwtProvider, UserRepository userRepository, PrincipalOauth2UserService principalOauth2UserService, Oauth2LoginSuccessHandler oauth2LoginSuccessHandler) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.securityDeniedHandler = new SecurityDeniedHandler();
        this.securityExceptionHandler = new SecurityExceptionHandler();
        this.principalOauth2UserService = principalOauth2UserService;
        this.oauth2LoginSuccessHandler = oauth2LoginSuccessHandler;
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
                        authorize.requestMatchers("/**").permitAll()
//                        authorize.requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/api/v1/member/auth/**").permitAll()
//                                // 카카오
//                                .requestMatchers("/oauth/**", "login/**", "loginForm/**").permitAll()
//                                // 스웨거
//                                .requestMatchers( "/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                                .requestMatchers( "/api/v1/member/product/**").permitAll()
//                                .requestMatchers( "/api/v1/member/article/**").permitAll()
//                                .requestMatchers( HttpMethod.GET, "/api/v1/member/review/**").permitAll()
//                                .requestMatchers( "/api/v1/member/productAnalyze/**").permitAll()
//                                .requestMatchers( "/api/v1/member/count/**").permitAll()
//                                // GET (User)
//                                .requestMatchers(HttpMethod.GET, "/api/v1/**").hasRole(ZeroDangRole.USER.name())
//
//                                // POST (User)
//                                .requestMatchers(HttpMethod.POST, "/api/v1/**").hasRole(ZeroDangRole.USER.name())
//
//                                // DELETE (User)
//                                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasRole(ZeroDangRole.USER.name())
//
//                                // PUT (User)
//                                .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasRole(ZeroDangRole.USER.name())
//
//                                // PATCH (User)
//                                .requestMatchers(HttpMethod.PATCH, "/api/v1/**").hasRole(ZeroDangRole.USER.name())
//                                .requestMatchers("/error").permitAll()
//                                .requestMatchers("/**").denyAll()
                )
                .oauth2Login(login -> login
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(principalOauth2UserService))
                        .successHandler(oauth2LoginSuccessHandler)
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

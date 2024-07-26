package com.example.zerodang.global.security;

import com.example.zerodang.domain.user.entity.UserStatus;
import com.example.zerodang.domain.user.repository.UserRepository;
import com.example.zerodang.global.exception.ErrorCode;
import com.example.zerodang.global.exception.user.UserNotActiveException;
import com.example.zerodang.global.exception.user.UserNotFoundException;
import com.example.zerodang.global.model.ZeroDangHeader;
import com.example.zerodang.global.security.jwt.JwtProvider;
import com.example.zerodang.global.security.jwt.ZeroDangRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final Logger log = LoggerFactory.getLogger(SecurityFilter.class);
    private final UserRepository userRepository;
    private static final Set<String> EXCLUDED_PATHS = Set.of(
            "/api/v1/member/user/auth/**",
            "/swagger-ui/**", "/v3/api-docs/**"
    );
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getRequestURI();

        if (EXCLUDED_PATHS.contains(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader(ZeroDangHeader.AUTHORIZATION.getKey());
        String prefix = "Bearer ";

        if (authorization == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!authorization.startsWith(prefix)) {
            log.info("{} :: {}", ErrorCode.WRONG_TOKEN.getMessage(), authorization);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(ErrorCode.WRONG_TOKEN.name());
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            return;
        }

        String jwt = authorization.replaceFirst(prefix, "");
//        String role = request.getHeader(ZeroDangHeader.AUTHORIZATION_ROLE.getKey());
        /**  에러 부분   **/
        String role = ZeroDangRole.USER.name(); // 임시로 일단 이걸로 하겠습니다.

        Long id = verifyUserId(jwt, role);
        if (id == null) {
            log.info("{} :: {}", ErrorCode.WRONG_TOKEN.getMessage(), authorization);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(ErrorCode.WRONG_TOKEN.name());
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            return;
        }

        saveContext(jwt, id, role);
        filterChain.doFilter(request, response);
    }


    // TODO 데이터베이스에 유저 정보가 존재하는지, 활성화된 유저인지 확인 필요
    private Long verifyUserId(String jwt, String role) {
        Long id = null;
        if (role.equals(ZeroDangRole.USER.name())) {
            id = jwtProvider.getMemberId(jwt);
        } else if (role.equals(ZeroDangRole.PARTNER.name())) {
            id = jwtProvider.getPartnerId(jwt);
        } else {
            throw new UserNotFoundException(ErrorCode.NOT_FOUND_USER);
        }

        // 데이터베이스에서 사용자 존재 여부 및 활성화 상태 확인
        boolean optionalUserId = userRepository.existsByIdAndStatus(id, UserStatus.ACTIVE);
        if (!optionalUserId) {
            throw new UserNotActiveException(ErrorCode.NOT_ACTIVE_USER);
        }

        return id;
    }

    private void saveContext(String jwt, Long id, String role) {
        SecurityContextHolder.getContext().setAuthentication(
                createAuthentication(jwt, id, role)
        );
    }

    private Authentication createAuthentication(String jwt, Long id, String role) {
        Collection<GrantedAuthority> grantedAuthority = createGrantedAuthorities(role);
        User user = new User(id.toString(), "", grantedAuthority);
        return new UsernamePasswordAuthenticationToken(user, jwt, grantedAuthority);
    }

    private Collection<GrantedAuthority> createGrantedAuthorities(String role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }
}

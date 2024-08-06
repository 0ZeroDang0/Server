package com.example.zerodang.domain.user.service;

import com.example.zerodang.domain.user.dto.request.UserRequestDTO;
import com.example.zerodang.domain.user.dto.response.UserResponseDTO;
import com.example.zerodang.domain.user.entity.User;
import com.example.zerodang.domain.user.mapper.UserMapper;
import com.example.zerodang.domain.user.repository.UserRepository;
import com.example.zerodang.global.exception.ErrorCode;
import com.example.zerodang.global.exception.user.UserEmailDuplicationException;
import com.example.zerodang.global.exception.user.UserInvalidPasswordException;
import com.example.zerodang.global.exception.user.UserNotFoundException;
import com.example.zerodang.global.security.jwt.JwtDto;
import com.example.zerodang.global.security.jwt.JwtProvider;
import com.example.zerodang.global.security.jwt.ZeroDangRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.zerodang.global.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public JwtDto login(UserRequestDTO.UserLoginDTO userLoginDTO) {
        User findUser = getUser_Email(userLoginDTO.getUserEmail());
        checkPassword(userLoginDTO.getUserPassword(), findUser, passwordEncoder);
        return jwtProvider.createJwtDto(findUser, ZeroDangRole.USER);
    }

    @Override
    @Transactional
    public UserResponseDTO.UserJoinDTO join(UserRequestDTO.UserJoinDTO userJoinDTO) {
        // 이메일 중복 확인
        if(userRepository.findByUserEmail(userJoinDTO.getUserEmail()).isPresent()) {
            throw new UserEmailDuplicationException(EMAIL_DUPLICATION_USER);
        }

        // User 생성 및 저장
        User user = userRepository.save(userMapper.toUserEntity(userJoinDTO, passwordEncoder));

        // UserResponseDTO.UserJoinDTO 반환
        return userMapper.toUserJoinResDTO(user);
    }

    /** 추가 메서드 **/
    private static void checkPassword(String password, User findUser, PasswordEncoder passwordEncoder) {
        if(!passwordEncoder.matches(password, findUser.getUserPassword())) {
            throw new UserInvalidPasswordException(INVALID_PASSWORD_USER);
        }
    }
    @Override
    public User getUser_Id(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER));
    }
    @Override
    public User getUser_Email(String userEmail) {
        return userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER));
    }
}

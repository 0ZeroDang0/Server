package com.example.zerodang.domain.user.mapper;

import com.example.zerodang.domain.user.dto.request.UserRequestDTO;
import com.example.zerodang.domain.user.dto.response.UserResponseDTO;
import com.example.zerodang.domain.user.entity.User;
import com.example.zerodang.domain.user.entity.UserStatus;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class UserMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public User toUserEntity(UserRequestDTO.UserJoinDTO userJoinDTO, PasswordEncoder passwordEncoder) {
        return User.builder()
                .userEmail(userJoinDTO.getUserEmail())
                .userPassword(passwordEncoder.encode(userJoinDTO.getUserPassword()))
                .userName(userJoinDTO.getUserName())
                .userStatus(UserStatus.ACTIVE)
                .lastLogin(LocalDateTime.now())
                .build();
    }


    public UserResponseDTO.UserJoinDTO toUserJoinResDTO(User user) {
        return modelMapper.map(user, UserResponseDTO.UserJoinDTO.class);
    }
}

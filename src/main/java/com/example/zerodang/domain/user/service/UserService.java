package com.example.zerodang.domain.user.service;

import com.example.zerodang.domain.user.dto.request.UserRequestDTO;
import com.example.zerodang.domain.user.dto.response.UserResponseDTO;
import com.example.zerodang.global.security.jwt.JwtDto;

public interface UserService {
    JwtDto login(UserRequestDTO.UserLoginDTO userLoginDTO);
    UserResponseDTO.UserJoinDTO join(UserRequestDTO.UserJoinDTO userJoinDTO);
}

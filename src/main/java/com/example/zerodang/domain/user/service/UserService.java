package com.example.zerodang.domain.user.service;

import com.example.zerodang.domain.user.dto.request.UserRequestDTO;
import com.example.zerodang.domain.user.dto.response.UserResponseDTO;
import com.example.zerodang.global.security.jwt.JwtDto;

public interface UserService {
    /** 로그인 **/
    JwtDto login(UserRequestDTO.UserLoginDTO userLoginDTO);
    /** 회원 가입 **/
    UserResponseDTO.UserJoinDTO join(UserRequestDTO.UserJoinDTO userJoinDTO);
    /** 로그아웃 **/
    /** 로그인 **/
}

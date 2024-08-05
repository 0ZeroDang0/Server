package com.example.zerodang.domain.user.controller;

import com.example.zerodang.domain.user.dto.request.UserRequestDTO;
import com.example.zerodang.domain.user.dto.response.UserResponseDTO;
import com.example.zerodang.domain.user.service.UserService;
import com.example.zerodang.global.response.CustomResponse;
import com.example.zerodang.global.security.jwt.JwtDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member/auth")
@Tag(name = "User", description = "회원 관련 API")
@RequiredArgsConstructor
@Slf4j
public class UserAuthApiController {
    private final UserService userService;

    /** 회원 로그인 API **/
    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "로그인 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = JwtDto.class)))
    })
    public ResponseEntity<?> login(@Parameter(description = "로그인 정보를 담고 있는 DTO")
                                   @RequestBody UserRequestDTO.UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), userService.login(userLoginDTO)));
    }

    /** 회원 가입 API **/
    @PostMapping("/join")
    @Operation(summary = "회원가입 API", description = "회원가입 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SUCCESS", content = @Content(schema = @Schema(implementation = UserResponseDTO.UserJoinDTO.class)))
    })
    public ResponseEntity<?> join(@Parameter(description = "회원가입 정보를 담고 있는 DTO")
                                  @RequestBody UserRequestDTO.UserJoinDTO userJoinDTO) {
        return ResponseEntity.ok().body(CustomResponse.SUCCESS(HttpStatus.CREATED.value(), userService.join(userJoinDTO)));
    }
}

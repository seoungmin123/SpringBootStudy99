package com.example.SpringBootStudy99.controller;

import com.example.SpringBootStudy99.common.ApiResponse;
import com.example.SpringBootStudy99.common.JwtTokenProvider;
import com.example.SpringBootStudy99.dto.LoginResultDto;
import com.example.SpringBootStudy99.dto.UserCreateRequstDto;
import com.example.SpringBootStudy99.dto.UserLoginDto;
import com.example.SpringBootStudy99.projection.UserResponseProjection;
import com.example.SpringBootStudy99.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final JwtTokenProvider jwtTokenProvider;

    //유저 전체 목록 조회
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<UserResponseProjection>>> getUserList(){
        List<UserResponseProjection> userList = userServiceImpl.getUserList();
        return ResponseEntity.ok(ApiResponse.success(userList));
    }

    //회원가입
    @PostMapping("/")
    public ResponseEntity<ApiResponse<?>> CreateUser(
            @RequestBody @Valid UserCreateRequstDto requstDto){
        ApiResponse<?> response  = userServiceImpl.createUser(requstDto);
        return  ResponseEntity.status(response.getStatus()).body(response);
    }

    //로그인 jwt
    @PostMapping("/jwt/login")
    public ResponseEntity<ApiResponse<?>> jwtLoginUser(@RequestBody UserLoginDto requstDto){
        ApiResponse<?> response = userServiceImpl.jwtLoginUser(requstDto);

        //로그인 실패시
        if (!response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)  // 401이나 400 사용 가능
                    .body(response);
        }

        LoginResultDto loginDto = (LoginResultDto) response.getData();

        //JWT발급 후
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginDto.getToken())
                .body(ApiResponse.success("로그인성공", loginDto));

        //JWT발급전
        //return ResponseEntity.status(response.getStatus()).body(response);

    }

    @GetMapping("/jwt/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (jwtTokenProvider.validateToken(token)) {
            String userId = jwtTokenProvider.getUserIdFromToken(token);
            return ResponseEntity.ok("안녕하세요, " + userId + "님");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않습니다.");
        }
    }

    //로그인 cookie
    @PostMapping("/cookie/login")
    public ResponseEntity<ApiResponse<?>> cookieLoginUser(@RequestBody UserLoginDto requstDto){
        ApiResponse<?> response = userServiceImpl.cookieLoginUser(requstDto);

        //로그인 실패시
        if (!response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)  // 401이나 400 사용 가능
                    .body(response);
        }

        LoginResultDto loginDto = (LoginResultDto) response.getData();

        //JWT발급 후
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginDto.getToken())
                .body(ApiResponse.success("로그인성공", loginDto));

        //JWT발급전
        //return ResponseEntity.status(response.getStatus()).body(response);

    }

    @GetMapping("/cookie/info")
    public ResponseEntity<?> getCookieUserInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (jwtTokenProvider.validateToken(token)) {
            String userId = jwtTokenProvider.getUserIdFromToken(token);
            return ResponseEntity.ok("안녕하세요, " + userId + "님");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않습니다.");
        }
    }


}

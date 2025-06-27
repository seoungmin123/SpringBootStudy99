package com.example.SpringBootStudy99.controller;

import com.example.SpringBootStudy99.common.ApiResponse;
import com.example.SpringBootStudy99.dto.LoginResultDto;
import com.example.SpringBootStudy99.dto.UserCreateRequstDto;
import com.example.SpringBootStudy99.dto.UserLoginDto;
import com.example.SpringBootStudy99.projection.UserResponseProjection;
import com.example.SpringBootStudy99.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

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

    //로그인
    @GetMapping("/login")
    public ResponseEntity<ApiResponse<?>> loginUser(@RequestBody UserLoginDto requstDto){
        ApiResponse<?> response = userServiceImpl.loginUser(requstDto);

        LoginResultDto loginDto = (LoginResultDto) response.getData();

        //JWT발급전
        //return ResponseEntity.status(response.getStatus()).body(response);

        //JWT발급 후
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginDto.getToken() )
                .body(ApiResponse.success("로그인성공",response));
    }

}

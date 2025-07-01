package com.example.SpringBootStudy99.service;

import com.example.SpringBootStudy99.common.ApiResponse;
import com.example.SpringBootStudy99.dto.UserCreateRequstDto;
import com.example.SpringBootStudy99.dto.UserLoginDto;
import com.example.SpringBootStudy99.projection.UserResponseProjection;

import java.util.List;

public interface UserService {
    //회원조회
    List<UserResponseProjection> getUserList();

    //회원가입
    ApiResponse<?> createUser(UserCreateRequstDto requestDto);

    //로그인 jwtLoginUser
    ApiResponse<?> jwtLoginUser(UserLoginDto requstDto);

    //로그인 쿠키 LoginUser
    ApiResponse<?> cookieLoginUser(UserLoginDto requstDto);





}

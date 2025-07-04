package com.example.SpringBootStudy99.service;

import com.example.SpringBootStudy99.common.ApiResponse;
import com.example.SpringBootStudy99.common.JwtTokenProvider;
import com.example.SpringBootStudy99.domain.user.UserRole;
import com.example.SpringBootStudy99.domain.user.UserVO;
import com.example.SpringBootStudy99.dto.LoginResultDto;
import com.example.SpringBootStudy99.dto.UserCreateRequstDto;
import com.example.SpringBootStudy99.dto.UserLoginDto;
import com.example.SpringBootStudy99.projection.UserResponseProjection;
import com.example.SpringBootStudy99.repository.UserRepository;
import com.example.SpringBootStudy99.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    //회원 전체조회
    @Override
    public List<UserResponseProjection> getUserList() {
        return userRepository.findAllProjectedBy();
    }

    //회원가입
    @Override
    //성공 실패 모르니까 ApiResponse<?>로 반환타입 받기로함
    public ApiResponse<?> createUser(UserCreateRequstDto requestDto) {
        String reqUserId = requestDto.getUserId();

        //유효성 체크 - 이미 아이디가 있는지 확인
        if(userRepository.existsById(reqUserId)){
            return ApiResponse.error(reqUserId +"는 이미 존재하는 아이디입니다.");
        }

        // 유저 객체 생성 및 필수 정보 세팅
        UserVO user = UserVO.builder()
                .userId(reqUserId)
                .userNm(requestDto.getUserNm())
                .userPwd(passwordEncoder.encode(requestDto.getUserPwd())) //  반드시 암호화 SecurityConfig.java
                .role(UserRole.ROLE_USER) // 기본 권한 설정 -> 무조건 USER
                .rgstDt(LocalDateTime.now())  //  직접 설정
                .build();

        //회원가입 정보 저장
        UserVO savedUser = userRepository.save(user);
        return ApiResponse.success("회원가입 성공", savedUser);
    }

    //로그인 jwt
    @Override
    public ApiResponse<?> jwtLoginUser(UserLoginDto requstDto) {
        String reqId = requstDto.getUserId();
        String reqPwd = requstDto.getUserPwd();

        System.out.println("입력한 아이디: " + reqId);
        System.out.println("입력한 비밀번호: " + reqPwd);

        //아이디 있는지 확인
        if(!userRepository.existsById(reqId)){
            return ApiResponse.error(reqId +" 아이디가 존재하지 않습니다.");
        }

        //있다면 비번 맞는지 확인
        UserVO userVO = userRepository.findByUserId(reqId);
        String userPwd = userVO.getUserPwd();
        System.out.println("DB 비밀번호: " + userPwd);


        if (passwordEncoder.matches(reqPwd, userPwd)) {//입력받은비번, DB에저장된암호화된비번
       // if (!reqPwd.equals(userPwd)){
            return ApiResponse.error(reqId + "/" +" 패스워드가 일치하지않습니다.");
        }

        // 토큰 발급전
        //return ApiResponse.success(userVO.getUserNm()+"님 로그인성공 하였습니다." , userVO);

        //JWT 토큰 발급하기!!!
        String token = jwtTokenProvider.generateToken(userVO.getUserId(),userVO.getRole()); //디비에서 꺼낸 권한을 가지고 권한찾기
        System.out.println(token);

        LoginResultDto loginDto =  new LoginResultDto(userVO.getUserNm(), token);
        return ApiResponse.success("로그인성공",loginDto);//

        }


    //로그인 쿠키
    @Override
    public ApiResponse<?> cookieLoginUser(UserLoginDto requstDto) {
        String reqId = requstDto.getUserId();
        String reqPwd = requstDto.getUserPwd();

        System.out.println("입력한 아이디: " + reqId);
        System.out.println("입력한 비밀번호: " + reqPwd);

        //아이디 있는지 확인
        if(!userRepository.existsById(reqId)){
            return ApiResponse.error(reqId +" 아이디가 존재하지 않습니다.");
        }

        //있다면 비번 맞는지 확인
        UserVO userVO = userRepository.findByUserId(reqId);
        System.out.println("DB 비밀번호: " + userVO.getUserPwd());

        String userPwd = userVO.getUserPwd();
        if (!reqPwd.equals(userPwd)){
            return ApiResponse.error(reqId + "/" +" 패스워드가 일치하지않습니다.");
        }

        // 토큰 발급전
        //return ApiResponse.success(userVO.getUserNm()+"님 로그인성공 하였습니다." , userVO);

        //JWT 토큰 발급하기!!!
        String token = jwtTokenProvider.generateToken(userVO.getUserId() , userVO.getRole());
        System.out.println(token);

        LoginResultDto loginDto =  new LoginResultDto(userVO.getUserNm(), token);
        return ApiResponse.success("로그인성공",loginDto);

    }

}

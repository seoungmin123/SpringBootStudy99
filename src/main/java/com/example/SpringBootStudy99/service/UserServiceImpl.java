package com.example.SpringBootStudy99.service;

import com.example.SpringBootStudy99.common.ApiResponse;
import com.example.SpringBootStudy99.common.JwtTokenProvider;
import com.example.SpringBootStudy99.domain.user.UserVO;
import com.example.SpringBootStudy99.dto.LoginResultDto;
import com.example.SpringBootStudy99.dto.UserCreateRequstDto;
import com.example.SpringBootStudy99.dto.UserLoginDto;
import com.example.SpringBootStudy99.projection.UserResponseProjection;
import com.example.SpringBootStudy99.repository.UserRepository;
import com.example.SpringBootStudy99.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final JwtTokenProvider jwtTokenProvider;

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
        };

        //아이디 비밀번호 유효성은 UserCreateRequstDto @Pattern 활용
        //받은 dto를 vo로 변경해서 저장해야댐
        //회원정보 저장
        userRepository.save( UserVO.from(requestDto));
        UserVO result = userRepository.findByUserId(reqUserId);
        return ApiResponse.success("회원가입 성공", result);
    }

    //로그인
    @Override
    public ApiResponse<?> loginUser(UserLoginDto requstDto) {
        String reqId = requstDto.getUserId();
        String reqPwd = requstDto.getUserPwd();

        //아이디 있는지 확인
        if(!userRepository.existsById(reqId)){
            return ApiResponse.error(reqId +" 아이디가 존재하지 않습니다.");
        };

        //있다면 비번 맞는지 확인
        UserVO userVO = userRepository.findByUserId(reqId);
        String userPwd = userVO.getUserPwd();
        if (!reqPwd.equals(userPwd)){
            return ApiResponse.error(reqId + "/" +" 패스워드가 일치하지않습니다.");
        }

        // 토큰 발급전
        //return ApiResponse.success(userVO.getUserNm()+"님 로그인성공 하였습니다." , userVO);

        //JWT 토큰 발급하기!!!
        String token = jwtTokenProvider.generateToken(userVO.getUserId());

        LoginResultDto loginDto =  new LoginResultDto(userVO.getUserNm(), token);
        return ApiResponse.success("로그인성공",loginDto);

        }


}

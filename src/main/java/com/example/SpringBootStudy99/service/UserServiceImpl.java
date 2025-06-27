package com.example.SpringBootStudy99.service;

import com.example.SpringBootStudy99.common.ApiResponse;
import com.example.SpringBootStudy99.domain.user.UserVO;
import com.example.SpringBootStudy99.dto.UserCreateRequstDto;
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
        UserResponseProjection result = userRepository.findByUserId(reqUserId);
        return ApiResponse.success("회원가입 성공", result);
    }

    @Override
    public List<UserResponseProjection> getUserList() {
        return userRepository.findAllProjectedBy();
    }
}

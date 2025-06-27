package com.example.SpringBootStudy99.validator;

import com.example.SpringBootStudy99.common.ValidateType;
import com.example.SpringBootStudy99.exception.UserNotFoundException;
import com.example.SpringBootStudy99.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    /** 사용자 존재 여부 확인 */
    public void validateUserExists(String userId , ValidateType vType) {
        boolean userExists = userRepository.existsById(userId);

        switch (vType){
            case USER -> {
                if (userExists) {
                    throw new UserNotFoundException("사용자 아이디 : " + userId + "는 이미 등록된 아이디입니다.");
                }
            }
            case BOARD -> {
                if (!userExists) {
                    throw new UserNotFoundException("등록되지 않은 사용자입니다. 사용자 아이디 : " + userId);
                }
            }
            //받지않은 타입일경우에
            default ->throw new EnumConstantNotPresentException(ValidateType.class, vType.name());
        }

    }


}


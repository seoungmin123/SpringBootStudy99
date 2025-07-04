package com.example.SpringBootStudy99.dto;

import com.example.SpringBootStudy99.domain.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequstDto {

    @NotBlank(message = "아이디는 필수입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)")
    private String userId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)")
    private String userPwd;

    @NotBlank(message = "이름은 필수입니다.")
    private String userNm;

    private UserRole role;

}

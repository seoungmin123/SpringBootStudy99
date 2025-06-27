package com.example.SpringBootStudy99.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResultDto {
    private String userName;
    private String token;

    public LoginResultDto(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

}

package com.example.SpringBootStudy99.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name="user")
public class UserVO {

    //속성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", length = 10)
    private String userId;

    @Column(name = "user_pwd", length = 30, nullable = false)
    private String userPwd;

    @Column(name = "user_nm", length = 15, nullable = false)
    private String userNm;

    @Column(name = "rgst_dt", nullable = false)
    private LocalDateTime rgstDt;

    //생성자
    public UserVO(String userId, String userPwd, String userNm){
        this.userId = userId;
        this.userPwd = userPwd;
        this.userNm = userNm;
        this.rgstDt = LocalDateTime.now();
    }

}

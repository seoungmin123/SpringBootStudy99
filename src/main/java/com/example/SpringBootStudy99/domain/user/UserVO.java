package com.example.SpringBootStudy99.domain.user;

import com.example.SpringBootStudy99.dto.UserCreateRequstDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name="user")
public class UserVO {

    //속성
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", length = 10)
    private String userId;

    @Column(name = "user_pwd", length = 30, nullable = false)
    private String userPwd;

    @Column(name = "user_nm", length = 15, nullable = false)
    private String userNm;

    @Column(name = "rgst_dt", nullable = false)
    private LocalDateTime rgstDt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @PrePersist
    public void prePersist() {
        if (this.rgstDt == null) {
            this.rgstDt = LocalDateTime.now();
        }
    }

    //생성자
    public UserVO(String userId, String userPwd, String userNm, UserRole userRole){
        this.userId = userId;
        this.userPwd = userPwd;
        this.userNm = userNm;
        this.role = userRole;
        this.rgstDt = LocalDateTime.now();
    }

    // 정적메서드 방식
    public static UserVO from(UserCreateRequstDto dto) {
        return new UserVO(
                dto.getUserId(),
                dto.getUserPwd(),
                dto.getUserNm(),
                dto.getRole()
        );
    }



}

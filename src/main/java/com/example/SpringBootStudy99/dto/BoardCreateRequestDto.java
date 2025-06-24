package com.example.SpringBootStudy99.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 게시글 작성 요청 DTO
public class BoardCreateRequestDto {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotBlank(message = "작성자는 필수입니다.")
    private String writer;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String boardPwd;
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
    private LocalDateTime rgstDt;
    private LocalDateTime udtDt;
}

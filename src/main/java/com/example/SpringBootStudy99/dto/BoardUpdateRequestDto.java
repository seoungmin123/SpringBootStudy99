package com.example.SpringBootStudy99.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 게시글 수정 요청 DTO
public class BoardUpdateRequestDto {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotBlank(message = "작성자명은 필수입니다.")
    private String writer;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String boardPwd;
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
}

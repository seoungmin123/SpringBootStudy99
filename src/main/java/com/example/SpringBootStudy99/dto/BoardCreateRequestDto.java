package com.example.SpringBootStudy99.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 게시글 작성 요청 DTO
public class BoardCreateRequestDto {
    private String title;
    private String writer;
    private String boardPwd;
    private String content;
}

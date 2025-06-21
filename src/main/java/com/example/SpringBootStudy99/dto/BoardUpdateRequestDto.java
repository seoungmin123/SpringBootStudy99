package com.example.SpringBootStudy99.dto;

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
    private String title;
    private String writer;
    private String boardPwd;
    private String content;
}

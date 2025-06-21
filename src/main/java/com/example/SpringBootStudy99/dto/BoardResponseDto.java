package com.example.SpringBootStudy99.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 게시글 응답 DTO (단건 조회용)
public class BoardResponseDto {
    private Long boardNo;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime rgstDt;
}

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
// 게시글 목록 응답 DTO
public class BoardListResponseDto {
    private Long id;
    private String title;
    private String username;
    private LocalDateTime createdAt;
}

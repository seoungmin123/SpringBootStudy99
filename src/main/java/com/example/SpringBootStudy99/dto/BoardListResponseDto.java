package com.example.SpringBootStudy99.dto;

import com.example.SpringBootStudy99.domain.board.BoardVO;
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
    private Long boardNo;
    private String title;
    private String writer;
    private LocalDateTime rgstDt;

    public static BoardListResponseDto from(BoardVO board) {
        return new BoardListResponseDto(
                board.getBoardNo(),
                board.getTitle(),
                board.getWriter(),
                board.getRgstDt()
        );
    }
}

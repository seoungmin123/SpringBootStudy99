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
// 게시글 응답 DTO (단건 조회용)
public class BoardResponseDto {
    private Long boardNo;
    private String title;
    private String writer;
    private String content;
    private Integer viewCnt;
    private LocalDateTime rgstDt;
    private LocalDateTime udtDt;

    // 정적 팩토리 메서드
    public static BoardResponseDto from(BoardVO boardVO) {
        return new BoardResponseDto(
                boardVO.getBoardNo(),
                boardVO.getTitle(),
                boardVO.getWriter(),
                boardVO.getContent(),
                boardVO.getViewCnt(),
                boardVO.getRgstDt(),
                boardVO.getUdtDt()
        );
    }
}



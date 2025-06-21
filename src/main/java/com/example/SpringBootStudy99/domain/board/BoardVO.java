package com.example.SpringBootStudy99.domain.board;

import com.example.SpringBootStudy99.dto.BoardCreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board")
public class BoardVO {

    //속성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;

    @Column(name = "writer", length = 10, nullable = false)
    private String writer;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @Column(name = "veiw_cnt")
    private int viewCnt = 0;

    @Column(name = "board_pwd", length = 30, nullable = false)
    private String boardPwd;

    @Column(name = "rgst_dt", nullable = false)
    private LocalDateTime rgstDt;

    @Column(name = "udt_dt")
    private LocalDateTime udtDt;

    // 생성자
    public BoardVO(String writer, String title, String content, String boardPwd, LocalDateTime rgstDt) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.boardPwd = boardPwd;
        this.rgstDt = rgstDt;
    }

    // 정적메서드 방식
    public static BoardVO from(BoardCreateRequestDto dto) {
        return new BoardVO(
                dto.getWriter(),
                dto.getTitle(),
                dto.getContent(),
                dto.getBoardPwd(),
                LocalDateTime.now()
        );
    }

}


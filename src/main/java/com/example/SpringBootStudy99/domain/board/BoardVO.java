package com.example.SpringBootStudy99.domain.board;

import com.example.SpringBootStudy99.dto.BoardCreateRequestDto;
import com.example.SpringBootStudy99.dto.BoardResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer viewCnt = 0 ; //null방지 Integer
    //생성자에서 초기화해줘도 되지만 그러면 정적팩토리 메서드할때 동일 생성자를 한개 더 만들어줘야한다(의미없고 번거로움)
    //순서랑 타입 일치해야해서

    @Column(name = "board_pwd", length = 30, nullable = false)
    private String boardPwd;

    @Column(name = "rgst_dt", nullable = false)
    private LocalDateTime rgstDt;

    @Column(name = "udt_dt")
    private LocalDateTime udtDt;

    // 생성자
    public BoardVO(String writer, String title, String content ,String boardPwd) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.boardPwd = boardPwd;
        this.rgstDt = LocalDateTime.now();
        this.udtDt = LocalDateTime.now();
    }


    // 정적메서드 방식
    public static BoardVO from(BoardCreateRequestDto dto) {
        return new BoardVO(
                dto.getWriter(),
                dto.getTitle(),
                dto.getContent(),
                dto.getBoardPwd()
        );
    }

    //기능
    //조회수 증가
    public void addViewCount() {
        this.viewCnt = (this.viewCnt == null ? 1 : this.viewCnt + 1);
    }

    //수정 메서드
    public void update(String title, String writer, String content) {
            this.title = title;

        if (writer != null && !writer.trim().isEmpty()) {
            this.writer = writer;
        }
            this.content = content;
    }

}


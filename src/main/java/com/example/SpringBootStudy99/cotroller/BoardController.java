package com.example.SpringBootStudy99.cotroller;

import com.example.SpringBootStudy99.domain.board.BoardVO;
import com.example.SpringBootStudy99.dto.BoardCreateRequestDto;
import com.example.SpringBootStudy99.dto.BoardListResponseDto;
import com.example.SpringBootStudy99.dto.BoardResponseDto;
import com.example.SpringBootStudy99.dto.BoardUpdateRequestDto;
import com.example.SpringBootStudy99.service.BoardServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardServiceImpl boardServiceImpl;

    // 생성자 주입 권장
    public BoardController(BoardServiceImpl boardServiceImpl) {
        this.boardServiceImpl = boardServiceImpl;
    }

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    //목록조회
    /*@GetMapping("/list")
    public String list(Model model) {
        List<BoardListResponseDto> boardList = boardServiceImpl.getBoardList();
        model.addAttribute("boardList", boardList);
        return "board/list"; // templates/board/list.html
    }*/

    @GetMapping("/list")
    public List<BoardListResponseDto> list() {
        return boardServiceImpl.getBoardList();  // JSON 응답
    }

//상세조회
/*
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        // 게시글 상세 조회 로직
        BoardResponseDto boardDetail = boardServiceImpl.getBoard(id);
        model.addAttribute("board", boardDetail);
        return "board/view"; // board/view.html 템플릿을 렌더링
    }
*/
    @GetMapping("/view/{id}")
    public BoardResponseDto detail(@PathVariable Long id) {
        return boardServiceImpl.getBoard(id);  // JSON 응답
    }

/*
    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("post", new BoardCreateRequestDto()); // 빈 게시글 객체 전달
        return "board/write"; // board/write.html 템플릿 렌더링
    }
*/
    //작성
    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardCreateRequestDto requestDto) {
        return boardServiceImpl.createBoard(requestDto);
    }

    //수정
    @PutMapping("/board/{id}")
    public BoardResponseDto updateBoard(
            @PathVariable Long id,
            @RequestBody BoardUpdateRequestDto requestDto) {
        return boardServiceImpl.updateBoard(id, requestDto);
    }

    //삭제
    @DeleteMapping("/board/{id}")
    public void deleteBoard(@PathVariable Long id, @RequestParam String password) {
        boardServiceImpl.deleteBoard(id, password);
    }



}

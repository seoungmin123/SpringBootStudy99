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
@RequestMapping("/api/board")
public class BoardController {

    private final BoardServiceImpl boardServiceImpl;

    // 생성자 주입
    public BoardController(BoardServiceImpl boardServiceImpl) {
        this.boardServiceImpl = boardServiceImpl;
    }

    //전체목록조회
    @GetMapping("/")
    public List<BoardListResponseDto> getAllBoards() {
        return boardServiceImpl.getBoardList();  // JSON 응답
    }

    //상세조회
    @GetMapping("/{id}")
    public BoardResponseDto getDetailBoard(@PathVariable Long id) {
        return boardServiceImpl.getBoard(id);  // JSON 응답
    }

    //작성
    @PostMapping("/")
    public BoardResponseDto insertBoard(@RequestBody BoardCreateRequestDto requestDto) {
        return boardServiceImpl.createBoard(requestDto);
    }

    //수정
    @PutMapping("/{id}")
    public BoardResponseDto updateBoard(
            @PathVariable Long id,
            @RequestBody BoardUpdateRequestDto requestDto) {
        return boardServiceImpl.updateBoard(id, requestDto);
    }

    //삭제
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id, @RequestParam String password) {
        boardServiceImpl.deleteBoard(id, password);
    }

/*
@GetMapping("/index")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<BoardListResponseDto> boardList = boardServiceImpl.getBoardList();
        model.addAttribute("boardList", boardList);
        return "board/list"; // templates/board/list.html
    }
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        // 게시글 상세 조회 로직
        BoardResponseDto boardDetail = boardServiceImpl.getBoard(id);
        model.addAttribute("board", boardDetail);
        return "board/view"; // board/view.html 템플릿을 렌더링
    }

    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("post", new BoardCreateRequestDto()); // 빈 게시글 객체 전달
        return "board/write"; // board/write.html 템플릿 렌더링
    }
*/

}

package com.example.SpringBootStudy99.cotroller;

import com.example.SpringBootStudy99.common.ApiResponse;
import com.example.SpringBootStudy99.domain.board.BoardVO;
import com.example.SpringBootStudy99.dto.BoardCreateRequestDto;
import com.example.SpringBootStudy99.dto.BoardListResponseDto;
import com.example.SpringBootStudy99.dto.BoardResponseDto;
import com.example.SpringBootStudy99.dto.BoardUpdateRequestDto;
import com.example.SpringBootStudy99.service.BoardServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board/")
public class BoardController {

    private final BoardServiceImpl boardServiceImpl;

    // 생성자 주입
    public BoardController(BoardServiceImpl boardServiceImpl) {
        this.boardServiceImpl = boardServiceImpl;
    }

    @GetMapping("/")
    public ApiResponse<List<BoardListResponseDto>>  list() {
        List<BoardListResponseDto> boardList = boardServiceImpl.getBoardList();
       return ApiResponse.success(boardList);
        //return ResponseEntity.ok(ApiResponse.success(boardServiceImpl.getBoardList()));

    }

    //상세조회
    @GetMapping("/{id}")
    public ApiResponse<BoardResponseDto> detail(@PathVariable Long id) {
        BoardResponseDto boardDetail = boardServiceImpl.getBoard(id);
        return ApiResponse.success(boardDetail);
    }

    //작성
    @PostMapping("/")
    public ApiResponse<BoardResponseDto> createBoard(@RequestBody BoardCreateRequestDto requestDto) {
        BoardResponseDto createBoard = boardServiceImpl.createBoard(requestDto);
        return ApiResponse.success(createBoard);
    }

    //수정
    @PutMapping("/{id}")
    public ApiResponse<BoardResponseDto> updateBoard(
            @PathVariable Long id,
            @RequestBody BoardUpdateRequestDto requestDto) {
        BoardResponseDto updateBoard = boardServiceImpl.updateBoard(id, requestDto);
        return ApiResponse.success(updateBoard);
    }

    //삭제
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBoard(@PathVariable Long id, @RequestParam String password) {
        boardServiceImpl.deleteBoard(id, password);
        return ApiResponse.success("게시글이 삭제되었습니다.");
    }

    /*@GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    //목록조회
    @GetMapping("/list")
    public String list(Model model) {
        List<BoardListResponseDto> boardList = boardServiceImpl.getBoardList();
        model.addAttribute("boardList", boardList);
        return "board/list"; // templates/board/list.html
    }*/

    /*
    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("post", new BoardCreateRequestDto()); // 빈 게시글 객체 전달
        return "board/write"; // board/write.html 템플릿 렌더링
    }
*/
    /*
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        // 게시글 상세 조회 로직
        BoardResponseDto boardDetail = boardServiceImpl.getBoard(id);
        model.addAttribute("board", boardDetail);
        return "board/view"; // board/view.html 템플릿을 렌더링
    }
*/


}

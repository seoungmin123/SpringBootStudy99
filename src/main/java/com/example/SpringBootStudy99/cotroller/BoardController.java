package com.example.SpringBootStudy99.controller;

import com.example.SpringBootStudy99.common.ApiResponse;
import com.example.SpringBootStudy99.dto.BoardCreateRequestDto;
import com.example.SpringBootStudy99.dto.BoardListResponseDto;
import com.example.SpringBootStudy99.dto.BoardResponseDto;
import com.example.SpringBootStudy99.dto.BoardUpdateRequestDto;
import com.example.SpringBootStudy99.service.BoardServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardServiceImpl;

    /** 전체 목록 조회 */
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<BoardListResponseDto>>> getAllBoards() {
        List<BoardListResponseDto> boardList = boardServiceImpl.getBoardList();
        return ResponseEntity.ok(ApiResponse.success(boardList));
    }

    /** 게시글 상세 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> getDetailBoard(@PathVariable Long id) {
        BoardResponseDto board = boardServiceImpl.getBoard(id);
        return ResponseEntity.ok(ApiResponse.success(board));
    }

    /** 게시글 작성 */
    @PostMapping("/")
    public ResponseEntity<ApiResponse<BoardResponseDto>> insertBoard(
                    @RequestBody @Valid BoardCreateRequestDto requestDto) {
        BoardResponseDto created = boardServiceImpl.createBoard(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("게시글이 생성되었습니다.", created));
    }

    /** 게시글 수정 */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> updateBoard(
            @PathVariable Long id,
            @RequestBody @Valid BoardUpdateRequestDto requestDto) {
        BoardResponseDto updated = boardServiceImpl.updateBoard(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success("게시글이 수정되었습니다.", updated));
    }

    /** 게시글 삭제 */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(
            @PathVariable Long id,
            @RequestParam String password) {
        boardServiceImpl.deleteBoard(id, password);
        return ResponseEntity.ok(ApiResponse.success("게시글이 삭제되었습니다.", null));
    }

}

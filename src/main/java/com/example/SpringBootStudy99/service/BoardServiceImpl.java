package com.example.SpringBootStudy99.service;

import com.example.SpringBootStudy99.domain.board.BoardVO;
import com.example.SpringBootStudy99.dto.BoardCreateRequestDto;
import com.example.SpringBootStudy99.dto.BoardListResponseDto;
import com.example.SpringBootStudy99.dto.BoardResponseDto;
import com.example.SpringBootStudy99.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardResponseDto boardRepository;

    //상세조회
    @Override
    public BoardResponseDto getBoard(Long id) {
        return null;
    }

    //목록조회
    @Override
    public List<BoardListResponseDto> getBoardList() {
        return List.of();
    }

    //작성
    @Override
    public BoardResponseDto createBoard(BoardCreateRequestDto requestDto) {
        //정적 메서드 방식을 사용했음 BoardVO에 추가함
        BoardVO boardVO = BoardVO.from(requestDto);
        boardRepository.save(boardVO);
        return null;
    }

    //수정
    @Override
    public BoardResponseDto updateBoard(Long id, BoardUpdateRequestDto requestDto) {
        //게시글 존재하면

        //게시글 비밀번호가 일치시

        //게시글 수정

        //게시글 반환
        return null;
    }

    //삭제
    @Override
    public void deleteBoard(Long id, String password) {
        //게시글 존재하면

        //게시글 비밀번호가 일치시

        //게시글 삭제


    }
}
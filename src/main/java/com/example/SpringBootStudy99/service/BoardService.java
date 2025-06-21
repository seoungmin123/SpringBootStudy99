package com.example.SpringBootStudy99.service;

import com.example.SpringBootStudy99.dto.BoardCreateRequestDto;
import com.example.SpringBootStudy99.dto.BoardListResponseDto;
import com.example.SpringBootStudy99.dto.BoardResponseDto;
import com.example.SpringBootStudy99.dto.BoardUpdateRequestDto;

import java.util.List;

public interface BoardService {
    BoardResponseDto getBoard(Long id);
    List<BoardListResponseDto> getBoardList();
    BoardResponseDto createBoard(BoardCreateRequestDto requestDto);
    BoardResponseDto updateBoard(Long id, BoardUpdateRequestDto requestDto);
    void deleteBoard(Long id, String password);
}

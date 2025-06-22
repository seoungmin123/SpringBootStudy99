package com.example.SpringBootStudy99.service;

import com.example.SpringBootStudy99.domain.board.BoardVO;
import com.example.SpringBootStudy99.dto.BoardCreateRequestDto;
import com.example.SpringBootStudy99.dto.BoardListResponseDto;
import com.example.SpringBootStudy99.dto.BoardResponseDto;
import com.example.SpringBootStudy99.dto.BoardUpdateRequestDto;
import com.example.SpringBootStudy99.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    //목록조회
    @Override
    @Transactional(readOnly = true)
    public List<BoardListResponseDto> getBoardList() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "rgstDt"))
                .stream()
                .map(BoardListResponseDto::from)
                .toList();
    }

    //상세조회
    @Override
    @Transactional
    public BoardResponseDto getBoard(Long id) {
        BoardVO board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        board.addViewCount(); // 조회수 증가

        return BoardResponseDto.from(board); // BoardVO → DTO 변환
    }

    //작성
    @Override
    @Transactional
    public BoardResponseDto createBoard(BoardCreateRequestDto requestDto) {
        //정적 메서드 방식을 사용했음 BoardVO에 추가함
        BoardVO boardVO = BoardVO.from(requestDto);
        boardRepository.save(boardVO);
        return BoardResponseDto.from(boardVO); // DTO로 변환해 반환
    }

    //수정
    @Override
    public BoardResponseDto updateBoard(Long id, BoardUpdateRequestDto requestDto) {
        //게시글 존재하면
        BoardVO board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        //게시글 비밀번호가 일치시
        if (!board.getBoardPwd().equals(requestDto.getBoardPwd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //게시글 수정
        board.update(requestDto.getTitle(), requestDto.getWriter(), requestDto.getContent());  // BoardVO 내부에서 setter 대신 update 메서드 사용

        //게시글 반환
        return BoardResponseDto.from(board);
    }

    //삭제
    @Override
    @Transactional
    public void deleteBoard(Long id, String password) {
        //게시글 존재하면
        BoardVO board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        //게시글 비밀번호가 일치시
        if (!board.getBoardPwd().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //게시글 삭제
        boardRepository.delete(board);

    }
}
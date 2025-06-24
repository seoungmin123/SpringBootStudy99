package com.example.SpringBootStudy99.service;

import com.example.SpringBootStudy99.domain.board.BoardVO;
import com.example.SpringBootStudy99.dto.BoardCreateRequestDto;
import com.example.SpringBootStudy99.dto.BoardListResponseDto;
import com.example.SpringBootStudy99.dto.BoardResponseDto;
import com.example.SpringBootStudy99.dto.BoardUpdateRequestDto;
import com.example.SpringBootStudy99.repository.BoardRepository;
import com.example.SpringBootStudy99.repository.UserRepository;
import com.example.SpringBootStudy99.validator.BoardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardValidator boardValidator;

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
        //유효성 체크
        BoardVO board = boardValidator.getBoardOrThrow(id); //게시글 존재 확인
        board.addViewCount(); // 조회수 증가

        return BoardResponseDto.from(board); // BoardVO → DTO 변환
    }

    //작성
    @Override
    @Transactional
    public BoardResponseDto createBoard(BoardCreateRequestDto requestDto) {
        //유효성 체크
        boardValidator.validateUserExists(requestDto.getWriter()); //등록되지 않은유저일경우

        //정적 메서드 방식을 사용했음 BoardVO에 추가함
        BoardVO boardVO = BoardVO.from(requestDto);

        boardRepository.save(boardVO);
        return BoardResponseDto.from(boardVO); // DTO로 변환해 반환
    }

    //수정
    @Override
    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardUpdateRequestDto requestDto) {
        //유효성 체크
        BoardVO board = boardValidator.getBoardOrThrow(id); //게시글 존재 확인
        boardValidator.validateBoardPassword(board , requestDto.getBoardPwd()); //게시글 비밀번호일치 확인

        //게시글 수정
        board.update(requestDto.getTitle(), requestDto.getWriter(), requestDto.getContent());  // BoardVO 내부에서 setter 대신 update 메서드 사용

        //게시글 반환
        return BoardResponseDto.from(board);
    }

    //삭제
    @Override
    @Transactional
    public void deleteBoard(Long id, String password) {
        //유효성 체크
        BoardVO board = boardValidator.getBoardOrThrow(id); //게시글 존재 확인
        boardValidator.validateBoardPassword(board , password); //게시글 비밀번호일치 확인

        //게시글 삭제
        boardRepository.delete(board);
    }




}
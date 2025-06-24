package com.example.SpringBootStudy99.validator;

import com.example.SpringBootStudy99.domain.board.BoardVO;
import com.example.SpringBootStudy99.exception.BoardNotFoundException;
import com.example.SpringBootStudy99.exception.InvalidPasswordException;
import com.example.SpringBootStudy99.exception.UserNotFoundException;
import com.example.SpringBootStudy99.repository.UserRepository;
import com.example.SpringBootStudy99.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardValidator {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;


    /** 사용자 존재 여부 확인 */
    public void validateUserExists(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("등록되지 않은 사용자입니다. 사용자 아이디 : " + userId);
        }
    }

    /** 게시글 존재 여부 확인 및 반환 */
    public BoardVO getBoardOrThrow(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("해당 게시글이 존재하지 않습니다. 게시글 번호 : " + boardId));
    }

    /** 게시글 비밀번호 일치 확인 */
    public void validateBoardPassword(BoardVO board, String inputPassword) {
        if (!board.getBoardPwd().equals(inputPassword)) {
            throw new InvalidPasswordException("게시글 비밀번호가 일치하지 않습니다. 입력한 비밀번호 : " + inputPassword);
        }
    }
}

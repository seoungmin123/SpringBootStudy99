package com.example.SpringBootStudy99.exception;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(Long id) {
        super("해당 게시글이 없습니다. id=" + id);
    }
}

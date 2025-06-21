package com.example.SpringBootStudy99.repository;

import com.example.SpringBootStudy99.domain.board.BoardVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardVO, Long> {
    // 기본 CRUD 메서드 제공됨 (save, findById, deleteById 등)
}

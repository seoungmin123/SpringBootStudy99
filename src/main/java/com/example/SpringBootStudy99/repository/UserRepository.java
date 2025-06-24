package com.example.SpringBootStudy99.repository;

import com.example.SpringBootStudy99.domain.user.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserVO, String> {
    boolean existsById(String userId);
}

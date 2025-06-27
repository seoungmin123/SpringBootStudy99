package com.example.SpringBootStudy99.repository;

import com.example.SpringBootStudy99.domain.user.UserVO;
import com.example.SpringBootStudy99.projection.UserResponseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserVO, String> {
    boolean existsById(String userId);
    UserVO findByUserId(String userId);
    List<UserResponseProjection> findAllProjectedBy();// Projection을 반환하도록 정의
}

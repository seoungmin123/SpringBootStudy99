package com.example.SpringBootStudy99.common;

import com.example.SpringBootStudy99.domain.user.UserRole;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    //private final String secretKey = "my-jwt-secret-key";
   // private final String secretKey = Base64.getEncoder().encodeToString("my-jwt-secret-key".getBytes());

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 자동으로 강한 키 생성

    private final long expiration = 1000 * 60 * 60; // 1시간

    //토큰 발급하기
    public String generateToken(String userId , UserRole role) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role.name()) // 역할도 클레임에 포함
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    //토큰 유효성 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    //유저 토큰 가져오기 토큰 본문에서 클레임(Claims) 추출 -> JWT의 sub(subject)에 저장된 userId를 꺼냄
    //이 값을 인증 필터에서 SecurityContextHolder에 넣어줌
    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //토큰으로부터 권한가져오기
    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }


}

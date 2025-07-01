package com.example.SpringBootStudy99.common;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    //private final String secretKey = "my-jwt-secret-key";
   // private final String secretKey = Base64.getEncoder().encodeToString("my-jwt-secret-key".getBytes());

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 자동으로 강한 키 생성

    private final long expiration = 1000 * 60 * 60; // 1시간

    //토큰 발급하기
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    //토큰 유효성 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    //유저 토큰 가져오기
    public String getUserIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

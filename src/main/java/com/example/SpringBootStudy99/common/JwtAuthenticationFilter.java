package com.example.SpringBootStudy99.common;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

//@RequiredArgsConstructor: final 필드인 jwtTokenProvider를 생성자로 주입받도록 해줌 (롬복 기능)
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { //OncePerRequestFilter: 매 요청마다 단 한 번만 실행되는 Spring Security 전용 필터

    //JWT 토큰을 검증하고 사용자 정보를 추출할 유틸 클래스
    //final로 선언 → 생성자로 반드시 주입되어야 함 (불변성 확보)
    private final JwtTokenProvider jwtTokenProvider;


    //여기서 HTTP 요청/응답을 가로채서 JWT 인증 처리를 수행함
    //doFilterInternal()은 OncePerRequestFilter의 핵심 메서드
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        // 1. 요청 헤더에서 Authorization 값 추출 - HTTP 요청의 Authorization 헤더 값을 가져옴
        //일반적으로 클라이언트는 이 헤더에 "Bearer <JWT토큰>" 형태로 토큰을 보냄
        String authorizationHeader = request.getHeader("Authorization");

        // 정상적인 JWT 토큰 요청이라면
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // "Bearer " 제거  순수한 토큰 문자열만 추출

            // 2. 토큰 유효성 검사
            if (jwtTokenProvider.validateToken(token)) { //올바른 토큰이면 true, 아니면 false 반환

                // 3. 토큰에서 사용자 아이디 추출
                String userId = jwtTokenProvider.getUserIdFromToken(token); //JWT 내부의 sub(subject) 또는 userId 클레임에서 사용자 식별자 추출 -> 인증객체를 만들기위함
                String role = jwtTokenProvider.getRoleFromToken(token);

                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

                // 4. 인증 객체 생성 → SecurityContext에 등록
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, authorities);

                //IP, 세션 ID 등의 요청 정보를 authentication 객체에 추가
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //SecurityContext에 인증 객체 등록 -현재 요청에 대해 "이 사용자는 인증됨"이라고 Spring Security에게 알려주는 것
                //이후 @AuthenticationPrincipal 같은 어노테이션도 동작 가능
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        //필터 체인에서 다음 필터 또는 실제 컨트롤러로 요청을 넘김
        filterChain.doFilter(request, response);
    }
}

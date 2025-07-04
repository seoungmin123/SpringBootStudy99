package com.example.SpringBootStudy99.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) //CSRF 사용 안 함
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //JWT 기반이므로 세션 비활성화 &
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() //api/auth/** (ex. 로그인, 회원가입 등)는 인증 없이 허용
                        .requestMatchers("/admin/**").hasRole("ADMIN") //hasRole("ADMIN") → ROLE_ADMIN을 의미함 (ROLE_ 접두어 자동 처리)
                        .anyRequest().authenticated()// 그 외 모든 요청은 인증된 사용자만 접근 가능
                )
                //매 요청마다 토큰을 검사하고, 인증 정보를 SecurityContext에 등록함
                //Bean으로 등록한 필터 사용
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) //UsernamePasswordAuthenticationFilter 앞에 직접 만든 JWT 인증 필터를 삽입
                .build();
    }

    //JWT 필터 Bean 등록
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

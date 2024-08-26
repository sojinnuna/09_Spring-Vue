package org.scoula.security.filter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.security.util.JwtProcessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer "; // 끝에 공백 있음

    private final JwtProcessor jwtProcessor;
    private final UserDetailsService userDetailsService;

    // 토큰을 이용해서 인증 정보를 받아오는 메소드
    private Authentication getAuthentication(String token) {
        // 토큰에서 사용자 이름 추출
        String username = jwtProcessor.getUsername(token);
        // 추출한 사용자 이름으로 UserDetails 가져오기
        UserDetails princiapl = userDetailsService.loadUserByUsername(username);
        // 로드된 사용자 정보를 기반으로 인증 토큰 생성
        // credentials는 자격증명으로 null이면 이미 인증을 완료됐거나 자격증명이 필요하지 않은 상태
        return new UsernamePasswordAuthenticationToken(princiapl, null, princiapl.getAuthorities());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Authorization 헤더에서 Bearer 토큰 추출
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        // 실제로 Bearer 토큰이 맞는지 확인
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            // 토큰에서 "Bearer "를 제외한 실제 코튼 부분 잘라내기
            String token = bearerToken.substring(BEARER_PREFIX.length());

        // 토큰에서 사용자 정보 추출 및 Authentication 객체 구성 후 SecurityContext에 저장
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        super.doFilter(request, response, filterChain);
    }
}


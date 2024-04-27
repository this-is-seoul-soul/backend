package com.seouldata.auth.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Order(0)
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    // TODO: 현재는 role이 USER로 고정되어 있음

    // JWT Token 유효성 검증 filter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("JwtAuthenticationFilter::doFilterInternal() called");
        String token = request.getHeader("Authorization"); // header에서 token 꺼내기

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String memberId = jwtProvider.validateToken(token);
        AbstractAuthenticationToken authentication = UsernamePasswordAuthenticationToken.authenticated(
                memberId,
                token,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        authentication.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
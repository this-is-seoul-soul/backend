package com.seouldata.auth.global.jwt;

import com.seouldata.auth.global.exception.AuthErrorCode;
import com.seouldata.auth.global.exception.AuthException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;

    private final RedisTemplate<String, String> redisTemplate;

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Access Token 생성 및 저장
     * @param id String
     * @return accessToken String
     */
    public String generateAccessToken(String id) {
        Claims claims = Jwts.claims().setSubject(id);
        Date now = new Date();

        log.info("access token 만기 시간: {}", new Date(now.getTime() + jwtProperties.getAccessTokenValidity()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getAccessTokenValidity()))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Refresh Token 생성 및 저장
     * @param id String
     * @return refreshToken String
     */
    public String generateRefreshToken(String id) {
        Claims claims = Jwts.claims().setSubject(id);
        Date now = new Date();

        log.info("refresh token 만기 시간: {}", new Date(now.getTime() + jwtProperties.getRefreshTokenValidity()));

        final String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getRefreshTokenValidity()))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
        storeToken(refreshToken, id);
        return refreshToken;
    }

    /**
     * cache에 refresh 토큰 저장
     * @param token String
     * @param id String
     */
    private void storeToken(String token, String id) {
        redisTemplate.opsForValue().set(
                id,
                token,
                jwtProperties.getRefreshTokenValidity(),
                TimeUnit.MILLISECONDS);
    }

    /**
     * cache에 blacklist 저장
     * @param token
     * @param id
     */
    public void storeBlacklist(String token, String id) {
        redisTemplate.opsForValue().set(
                token,
                id,
                jwtProperties.getAccessTokenValidity(),
                TimeUnit.SECONDS);
    }

    /**
     * cache에서 refreshToken 삭제
     * @param id
     */
    public void deleteToken(String id) {
        redisTemplate.delete(id);
    }


    /**
     * 토큰 유효성 검증
     * @param token String
     * @return memberId String
     */
    public String validateToken (String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
        try {
            jwtParser.parse(token);

            // blacklist 확인
            if (redisTemplate.hasKey(token)) {
                throw new AuthException(AuthErrorCode.INVALID_TOKEN);
            }

            return extractMemberId(token);
        } catch (MalformedJwtException | IllegalArgumentException e) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    /**
     * 토큰에서 memberId 추출
     * @param token String
     * @return
     */
    public String extractMemberId(String token) {
        log.debug("extractMemberId() called");

        String id = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        log.debug("Token 추출 memberId: {}", id);
        return id;
    }

    /**
     * refresh token 유효성 검증
     * @param refreshToken
     * @return
     */
    public String validateRefreshToken (String refreshToken) {
        final String id = validateToken(refreshToken);
        final String storedRefreshToken = redisTemplate.opsForValue().get(id);
        if(!Objects.equals(refreshToken, storedRefreshToken)) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
        return id;
    }


    /**
     * access tokn 재발급
     * @param refreshToken
     * @return accessToken
     */
    public String reIssue(String refreshToken) {
        validateRefreshToken(refreshToken);
        return generateAccessToken(extractMemberId(refreshToken));
    }

}
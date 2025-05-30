package com.ssafy.live.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ssafy.live.domain.user.dto.UserDto;
import com.ssafy.live.security.auth.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String rawSecret;

    private SecretKey secretKey;

    private final long tokenValidTime = 1000L * 60 * 60; // 1시간

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(rawSecret.getBytes(StandardCharsets.UTF_8));
    }

    // 유저 정보를 기반으로 JWT 생성

    public String createToken(UserDto user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + tokenValidTime);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .claim("jti", UUID.randomUUID().toString()) // 고유한 ID로 매번 다른 토큰 생성
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    // JWT → Authentication 객체로 변환
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String email = claims.getSubject();
        String role = claims.get("role", String.class);
        Integer id = claims.get("id", Integer.class);

        CustomUserDetails userDetails = new CustomUserDetails(id, email, null, role); // ✅ id 포함
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // JWT 파싱해서 Claims 추출
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 유효성 검사
    public boolean validateToken(String token) {
        try {
            Date expiration = getClaims(token).getExpiration();
            System.out.println("Token Expiration: " + expiration);
            return expiration.after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("JWT 검증 실패: " + e.getMessage());
            return false;
        }
    }

    // 클레임에서 개별 필드 추출 (Optional)
    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public Integer getUserId(String token) {
        return getClaims(token).get("id", Integer.class);
    }

    // refresh 토큰 생성 메서드
    public String createRefreshToken(UserDto user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 1000L * 60 * 60 * 24 * 14); // 14일
        String jti = UUID.randomUUID().toString();

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("jti", jti)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public long getRemainingTime(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expiration.getTime() - System.currentTimeMillis();
    }
}

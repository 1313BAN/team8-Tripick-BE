package com.ssafy.live.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ssafy.live.security.auth.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String rawSecret;

    private SecretKey secretKey;

    private final long tokenValidTime = 1000L * 60 * 60; // 1시간


    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(
            Base64.getEncoder().encodeToString(rawSecret.getBytes(StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8)
        );
    }


    public String createToken(String email, String role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidTime);

        return Jwts.builder()
            .subject(email)
            .claim("role", role)
            .issuedAt(now)
            .expiration(validity)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact();
    }


    public Authentication getAuthentication(String token) {
        String email = getEmail(token);
        CustomUserDetails userDetails = new CustomUserDetails(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmail(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        return claims.getSubject();
    }


    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(secretKey) // ✅ only one argument
                .build()
                .parseSignedClaims(token)
                .getPayload();

            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


}

package com.ssafy.live.domain.user.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.ssafy.live.domain.user.dto.AuthResponseDto;
import com.ssafy.live.domain.user.dto.LoginRequestDto;
import com.ssafy.live.domain.user.service.UserService;
import com.ssafy.live.security.auth.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        AuthResponseDto tokens = userService.login(request);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokens.getRefreshToken())
                .httpOnly(true)
                .secure(false) // 개발환경에서는 false로
                .path("/")
                .maxAge(Duration.ofDays(14))
                .sameSite(
                        "Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("accessToken", tokens.getAccessToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal CustomUserDetails user) {
        userService.logout(user);

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false) // 개발환경에서는 false로
                .path("/")
                .maxAge(0)
                .sameSite(
                        "None")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(Map.of("message", "로그아웃 완료"));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@CookieValue("refreshToken") String refreshToken) {
        try {
            String newAccessToken = userService.reissue(refreshToken);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

}

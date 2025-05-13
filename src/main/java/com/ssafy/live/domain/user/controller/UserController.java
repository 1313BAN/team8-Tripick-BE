package com.ssafy.live.domain.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.live.domain.user.dto.LoginRequestDto;
import com.ssafy.live.domain.user.dto.SignupRequestDto;
import com.ssafy.live.domain.user.dto.UserDto;
import com.ssafy.live.domain.user.service.UserService;
import com.ssafy.live.security.auth.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequestDto request) {
        userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {
        String token = userService.login(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMyInfo(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(userService.getMyUserInfo(user));
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMyInfo(@RequestBody UserDto userDto,
                                          @AuthenticationPrincipal CustomUserDetails user) {
        userService.updateMyUser(userDto, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteMyAccount(@AuthenticationPrincipal CustomUserDetails user) {
        userService.deleteMyUser(user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body,
                                            @AuthenticationPrincipal CustomUserDetails user) {
        String current = body.get("currentPassword");
        String next = body.get("newPassword");
        userService.changePassword(current, next, user);
        return ResponseEntity.ok().build();
    }
}

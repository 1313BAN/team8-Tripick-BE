package com.ssafy.live.domain.user.controller;

import com.ssafy.live.domain.user.dto.*;
import com.ssafy.live.domain.user.service.UserService;
import com.ssafy.live.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/me")
    public ResponseEntity<UserDetailDto> getMyInfo(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(userService.getMyUserInfo(user));
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMyInfo(@RequestBody UserUpdateDto request,
                                              @AuthenticationPrincipal CustomUserDetails user) {
        userService.updateMyUser(request, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal CustomUserDetails user) {
        userService.deleteMyUser(user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeRequestDto request,
                                               @AuthenticationPrincipal CustomUserDetails user) {
        userService.changePassword(request.getCurrentPassword(), request.getNewPassword(), user);
        return ResponseEntity.ok().build();
    }
}
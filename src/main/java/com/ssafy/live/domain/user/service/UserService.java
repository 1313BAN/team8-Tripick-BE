package com.ssafy.live.domain.user.service;

import org.springframework.security.core.Authentication;

import com.ssafy.live.domain.user.dto.LoginRequestDto;
import com.ssafy.live.domain.user.dto.SignupRequestDto;
import com.ssafy.live.domain.user.dto.UserDto;

public interface UserService {
    void signup(SignupRequestDto request);
    String login(LoginRequestDto request); // 로그인 시 JWT 토큰 반환
    UserDto getMyUserInfo(Authentication auth);
    void updateMyUser(UserDto userDto, Authentication auth);
    void deleteMyUser(Authentication auth);
    void changePassword(String currentPassword, String newPassword, Authentication auth);

}
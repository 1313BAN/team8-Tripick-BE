package com.ssafy.live.domain.user.service;

import com.ssafy.live.domain.user.dto.LoginRequestDto;
import com.ssafy.live.domain.user.dto.SignupRequestDto;
import com.ssafy.live.domain.user.dto.UserDto;
import com.ssafy.live.security.auth.CustomUserDetails;

public interface UserService {
    void signup(SignupRequestDto request);
    String login(LoginRequestDto request);

    UserDto getMyUserInfo(CustomUserDetails user);
    void updateMyUser(UserDto userDto, CustomUserDetails user);
    void deleteMyUser(CustomUserDetails user);
    void changePassword(String currentPassword, String newPassword, CustomUserDetails user);
}

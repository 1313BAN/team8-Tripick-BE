package com.ssafy.live.domain.user.service;

import com.ssafy.live.domain.user.dto.*;
import com.ssafy.live.security.auth.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    void signup(SignupRequestDto request);

    AuthResponseDto login(LoginRequestDto request);

    UserDetailDto getMyUserInfo(CustomUserDetails user);

    void updateMyUser(UserUpdateDto userDto, CustomUserDetails user);

    void deleteMyUser(CustomUserDetails user);

    void changePassword(String currentPassword, String newPassword, CustomUserDetails user);

    void logout(CustomUserDetails user, HttpServletRequest request);

    AuthResponseDto reissueWithNewRefresh(String oldRefreshToken);
}

package com.ssafy.live.domain.user.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.live.domain.user.dao.UserDao;
import com.ssafy.live.domain.user.dto.LoginRequestDto;
import com.ssafy.live.domain.user.dto.SignupRequestDto;
import com.ssafy.live.domain.user.dto.UserDto;
import com.ssafy.live.security.auth.CustomUserDetails;
import com.ssafy.live.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(SignupRequestDto request) {
        UserDto user = request.toUserDto(passwordEncoder);
        userDao.insertUser(user);
    }

    @Override
    public String login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 인증 성공 후, principal로부터 email만 추출
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // JWT 발급 시 정확한 id 포함을 위해 DB에서 UserDto 다시 조회
        UserDto user = userDao.findByEmail(userDetails.getEmail());

        return jwtTokenProvider.createToken(user); // ✅ id, email, role 포함된 JWT 발급
    }


    @Override
    public UserDto getMyUserInfo(CustomUserDetails user) {
        return userDao.findById(user.getId()); // ✅ id 기반
    }

    @Override
    public void updateMyUser(UserDto userDto, CustomUserDetails user) {
        userDto.setId(user.getId()); // ✅ 바로 설정
        userDao.updateUser(userDto);
    }

    @Override
    public void deleteMyUser(CustomUserDetails user) {
        userDao.deleteUser(user.getId()); // ✅ 바로 삭제
    }

    @Override
    public void changePassword(String currentPassword, String newPassword, CustomUserDetails user) {
        UserDto userInDb = userDao.findById(user.getId()); // ✅ 안정적인 조회

        if (!passwordEncoder.matches(currentPassword, userInDb.getPassword())) {
            throw new AccessDeniedException("기존 비밀번호가 일치하지 않습니다.");
        }

        String hashedPassword = passwordEncoder.encode(newPassword);
        userDao.updatePassword(user.getId(), hashedPassword);
    }
}

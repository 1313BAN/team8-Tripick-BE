package com.ssafy.live.domain.user.service;

import org.springframework.security.access.AccessDeniedException;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(SignupRequestDto request) {
        UserDto user = new UserDto();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());
        user.setName(request.getName());
        user.setGender(request.getGender());
        user.setRole("USER");

        userDao.insertUser(user);
    }

    @Override
    public String login(LoginRequestDto request) {
        UserDto user = userDao.findByEmail(request.getEmail());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        return jwtTokenProvider.createToken(user.getEmail(), user.getRole());
    }

    @Override
    public UserDto getMyUserInfo(Authentication auth) {
        String email = ((CustomUserDetails) auth.getPrincipal()).getUsername();
        return userDao.findByEmail(email);
    }

    @Override
    public void updateMyUser(UserDto userDto, Authentication auth) {
        String email = ((CustomUserDetails) auth.getPrincipal()).getUsername();
        UserDto loginUser = userDao.findByEmail(email);
        userDto.setId(loginUser.getId());
        userDao.updateUser(userDto);
    }

    @Override
    public void deleteMyUser(Authentication auth) {
        String email = ((CustomUserDetails) auth.getPrincipal()).getUsername();
        UserDto loginUser = userDao.findByEmail(email);
        userDao.deleteUser(loginUser.getId());
    }
    
    @Override
    public void changePassword(String currentPassword, String newPassword, Authentication auth) {
        String email = ((CustomUserDetails) auth.getPrincipal()).getUsername();
        UserDto user = userDao.findByEmail(email);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new AccessDeniedException("기존 비밀번호가 일치하지 않습니다.");
        }

        String hashed = passwordEncoder.encode(newPassword);
        userDao.updatePassword(user.getId(), hashed);
    }


}

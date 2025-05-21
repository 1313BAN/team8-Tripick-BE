package com.ssafy.live.domain.user.service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.live.domain.user.dao.UserDao;
import com.ssafy.live.domain.user.dto.AuthResponseDto;
import com.ssafy.live.domain.user.dto.LoginRequestDto;
import com.ssafy.live.domain.user.dto.SignupRequestDto;
import com.ssafy.live.domain.user.dto.UserDto;
import com.ssafy.live.security.auth.CustomUserDetails;
import com.ssafy.live.security.jwt.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String REFRESH_PREFIX = "refresh:"; // prefix 상수

    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void signup(SignupRequestDto request) {
        UserDto user = request.toUserDto(passwordEncoder);
        userDao.insertUser(user);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        Authentication authentication = authenticateUser(request);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        UserDto user = new UserDto();
        user.setId(userDetails.getId());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());

        String accessToken = jwtTokenProvider.createToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        saveRefreshToken(user.getEmail(), refreshToken);

        return new AuthResponseDto(accessToken, refreshToken);
    }

    private Authentication authenticateUser(LoginRequestDto request) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    }

    private void saveRefreshToken(String email, String refreshToken) {
        redisTemplate.opsForValue().set(
                REFRESH_PREFIX + email,
                refreshToken,
                Duration.ofDays(14));
    }

    @Override
    public UserDto getMyUserInfo(CustomUserDetails user) {
        return userDao.findById(user.getId());
    }

    @Override
    public void updateMyUser(UserDto userDto, CustomUserDetails user) {
        userDto.setId(user.getId());
        userDao.updateUser(userDto);
    }

    @Override
    public void deleteMyUser(CustomUserDetails user) {
        userDao.deleteUser(user.getId());
    }

    @Override
    public void changePassword(String currentPassword, String newPassword, CustomUserDetails user) {
        UserDto userInDb = userDao.findById(user.getId());

        if (!passwordEncoder.matches(currentPassword, userInDb.getPassword())) {
            throw new AccessDeniedException("기존 비밀번호가 일치하지 않습니다.");
        }

        String hashedPassword = passwordEncoder.encode(newPassword);
        userDao.updatePassword(user.getId(), hashedPassword);
    }

    @Override
    public void logout(CustomUserDetails user, HttpServletRequest request) {
        // Refresh 토큰 제거
        redisTemplate.delete(REFRESH_PREFIX + user.getEmail());

        // Access Token 블랙리스트 등록
        String token = resolveAccessToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            long expiration = jwtTokenProvider.getRemainingTime(token);
            redisTemplate.opsForValue().set("blacklist:" + token, "true", Duration.ofMillis(expiration));
        }
    }

    @Override
    public String reissue(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new AccessDeniedException("Refresh Token이 만료되었습니다.");
        }

        String email = jwtTokenProvider.getEmail(refreshToken);
        String storedToken = redisTemplate.opsForValue().get(REFRESH_PREFIX + email);

        if (storedToken == null || !storedToken.equals(refreshToken)) {
            throw new AccessDeniedException("Refresh Token이 유효하지 않습니다.");
        }

        UserDto user = userDao.findByEmail(email);
        return jwtTokenProvider.createToken(user);
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }

}

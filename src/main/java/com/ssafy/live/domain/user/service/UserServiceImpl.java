package com.ssafy.live.domain.user.service;

import com.ssafy.live.domain.user.dao.UserDao;
import com.ssafy.live.domain.user.dto.*;
import com.ssafy.live.security.auth.CustomUserDetails;
import com.ssafy.live.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String REFRESH_PREFIX = "refresh:";

    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void signup(SignupRequestDto request) {
        UserDto user = request.toUserDto(passwordEncoder);
        userDao.insertUser(user);
        if (user.getMotiveCodes() != null) {
            for (Integer code : user.getMotiveCodes()) {
                userDao.insertUserMotive(user.getId(), code);
            }
        }
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
    public UserDetailDto getMyUserInfo(CustomUserDetails user) {
        UserDetailDto dto = userDao.selectUserDetail(user.getId());
        dto.setMotiveCodes(userDao.findUserMotiveCodes(user.getId()));
        dto.setMotiveNames(userDao.findUserMotiveNames(user.getId()));
        return dto;
    }

    @Override
    public void updateMyUser(UserUpdateDto dto, CustomUserDetails user) {
        dto.setId(user.getId());
        userDao.updateUser(dto);
        userDao.deleteUserMotives(user.getId());
        if (dto.getMotiveCodes() != null) {
            for (Integer code : dto.getMotiveCodes()) {
                userDao.insertUserMotive(user.getId(), code);
            }
        }
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
        redisTemplate.delete(REFRESH_PREFIX + user.getEmail());

        String token = resolveAccessToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            long expiration = jwtTokenProvider.getRemainingTime(token);
            redisTemplate.opsForValue().set("blacklist:" + token, "true", Duration.ofMillis(expiration));
        }
    }

    @Override
    public AuthResponseDto reissueWithNewRefresh(String oldRefreshToken) {
        if (!jwtTokenProvider.validateToken(oldRefreshToken)) {
            throw new AccessDeniedException("Refresh Token이 만료되었습니다.");
        }

        String email = jwtTokenProvider.getEmail(oldRefreshToken);
        String storedToken = redisTemplate.opsForValue().get(REFRESH_PREFIX + email);

        if (storedToken == null || !storedToken.equals(oldRefreshToken)) {
            throw new AccessDeniedException("Refresh Token이 유효하지 않습니다.");
        }

        UserDto user = userDao.findByEmail(email);

        String newAccessToken = jwtTokenProvider.createToken(user);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user);
        saveRefreshToken(email, newRefreshToken);

        return new AuthResponseDto(newAccessToken, newRefreshToken);
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }
}

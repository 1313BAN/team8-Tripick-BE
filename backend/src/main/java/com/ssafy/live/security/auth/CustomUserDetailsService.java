package com.ssafy.live.security.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.live.domain.user.dao.UserDao;
import com.ssafy.live.domain.user.dto.UserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("유저를 찾을 수 없습니다: " + email);
        }
        return new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword(), user.getRole());
    }
}

package com.ssafy.live.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class SignupRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String gender;
    private int age;

    public UserDto toUserDto(PasswordEncoder encoder) {
        UserDto user = new UserDto();
        user.setEmail(this.email);
        user.setPassword(encoder.encode(this.password)); // 🔐 암호화
        user.setNickname(this.nickname);
        user.setName(this.name);
        user.setGender(this.gender);
        user.setAge(this.age);
        user.setRole("USER"); // 기본 사용자 권한
        return user;
    }
}

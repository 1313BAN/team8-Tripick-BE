package com.ssafy.live.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Getter
@Setter
public class SignupRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String gender;
    private int age;

    private Integer accompanyCode;
    private Integer residenceSidoCode;
    private List<Integer> motiveCodes;

    public UserDto toUserDto(PasswordEncoder encoder) {
        UserDto user = new UserDto();
        user.setEmail(this.email);
        user.setPassword(encoder.encode(this.password));
        user.setNickname(this.nickname);
        user.setName(this.name);
        user.setGender(this.gender);
        user.setAge(this.age);
        user.setAccompanyCode(this.accompanyCode);
        user.setResidenceSidoCode(this.residenceSidoCode);
        user.setMotiveCodes(this.motiveCodes);
        user.setRole("USER");
        user.setRealUser(true); // default로 true 설정
        return user;
    }
}

package com.ssafy.live.domain.user.dto;
import lombok.Data;

@Data
public class SignupRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String gender;
    private int age;
}

package com.ssafy.live.domain.user.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserUpdateDto {
    private int id;
    private String nickname;
    private String name;
    private String gender;
    private int age;
    private String bio;
    private String profileImage;

    private Integer accompanyCode;
    private Integer residenceSidoCode;
    private List<Integer> motiveCodes;
}

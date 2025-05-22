package com.ssafy.live.domain.user.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String nickname;
    private String name;
    private String email;
    private String password;
    private String profileImage;
    private String role;
    private String bio;
    private String gender;
    private int age;

    private Integer accompanyCode; // 동행 유형
    private Integer residenceSidoCode; // 거주 지역
    private Boolean realUser; // default true

    private List<Integer> motiveCodes; // 유저가 선택한 여행 동기

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}

package com.ssafy.live.domain.user.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class UserDetailDto {
    private int id;
    private String email;
    private String nickname;
    private String name;
    private String gender;
    private int age;

    private Integer accompanyCode;
    private String accompanyLabel;

    private Integer residenceSidoCode;
    private String residenceSidoName;

    private List<Integer> motiveCodes;
    private List<String> motiveNames;

    private String profileImage;
    private String bio;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}

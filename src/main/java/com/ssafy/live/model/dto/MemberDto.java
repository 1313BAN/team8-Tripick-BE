package com.ssafy.live.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private int id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private Timestamp createdAt;
}

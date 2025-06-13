package com.ssafy.live;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin1234";  // ← 여기에 원하는 비밀번호 입력
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("✅ 암호화된 비밀번호:");
        System.out.println(encodedPassword);
    }
}

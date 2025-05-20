package com.ssafy.live.domain.user.controller;

import com.ssafy.live.domain.user.dto.LoginRequestDto;
import com.ssafy.live.domain.user.dto.SignupRequestDto;
import com.ssafy.live.domain.user.dto.UserDto;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserIntegrationTest {

    @LocalServerPort
    int port;

    RestTemplate rest = new RestTemplate();
    static String jwtToken;

    String getUserUrl(String path) {
        return "http://localhost:" + port + "/api/users" + path;
    }

    String getAuthUrl(String path) {
        return "http://localhost:" + port + "/api/auth" + path;
    }

    HttpHeaders authHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        return headers;
    }

    @Test
    @Order(1)
    void signupTest() {
        SignupRequestDto req = new SignupRequestDto();
        req.setEmail("jwtuser@example.com");
        req.setPassword("test1234!");
        req.setName("JWT유저");
        req.setNickname("jwt닉");
        req.setGender("MALE");
        req.setAge(30);

        ResponseEntity<Void> res = rest.postForEntity(getUserUrl("/signup"), req, Void.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(2)
    void loginTest() {
        LoginRequestDto login = new LoginRequestDto();
        login.setEmail("jwtuser@example.com");
        login.setPassword("test1234!");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequestDto> entity = new HttpEntity<>(login, headers);

        ResponseEntity<Map> response = rest.exchange(
                getAuthUrl("/login"),
                HttpMethod.POST,
                entity,
                Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        jwtToken = (String) response.getBody().get("accessToken");
        assertThat(jwtToken).isNotNull();
    }

    @Test
    @Order(3)
    void getMyInfoTest() {
        HttpEntity<?> entity = new HttpEntity<>(authHeader());

        ResponseEntity<UserDto> res = rest.exchange(getUserUrl("/me"), HttpMethod.GET, entity, UserDto.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody().getEmail()).isEqualTo("jwtuser@example.com");
    }

    @Test
    @Order(4)
    void updateMyInfoTest() {
        UserDto update = new UserDto();
        update.setNickname("변경된닉");
        update.setName("변경된이름");
        update.setAge(31);
        update.setGender("FEMALE");

        HttpEntity<UserDto> entity = new HttpEntity<>(update, authHeader());

        ResponseEntity<Void> res = rest.exchange(getUserUrl("/me"), HttpMethod.PUT, entity, Void.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(5)
    void changePasswordTest() {
        Map<String, String> body = Map.of(
                "currentPassword", "test1234!",
                "newPassword", "newpass123!");

        HttpEntity<?> entity = new HttpEntity<>(body, authHeader());

        ResponseEntity<Void> res = rest.exchange(getUserUrl("/me/password"), HttpMethod.PUT, entity, Void.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(6)
    void deleteUserTest() {
        HttpEntity<?> entity = new HttpEntity<>(authHeader());

        ResponseEntity<Void> res = rest.exchange(getUserUrl("/me"), HttpMethod.DELETE, entity, Void.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @Order(7)
    void logoutTest() {
        HttpEntity<?> entity = new HttpEntity<>(authHeader());

        ResponseEntity<Map> res = rest.exchange(getAuthUrl("/logout"), HttpMethod.POST, entity, Map.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody().get("message")).isEqualTo("로그아웃 완료");
    }
}

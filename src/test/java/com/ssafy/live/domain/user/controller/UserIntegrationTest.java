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

    String baseUrl;
    RestTemplate rest = new RestTemplate();
    static String jwtToken;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/users";
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

        ResponseEntity<Void> res = rest.postForEntity(baseUrl + "/signup", req, Void.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(2)
    void loginTest() {
        LoginRequestDto login = new LoginRequestDto();
        login.setEmail("jwtuser@example.com");
        login.setPassword("test1234!");

        ResponseEntity<String> res = rest.postForEntity(baseUrl + "/login", login, String.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        jwtToken = res.getBody();
        assertThat(jwtToken).isNotNull();
    }

    @Test
    @Order(3)
    void getMyInfoTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<UserDto> res = rest.exchange(baseUrl + "/me", HttpMethod.GET, entity, UserDto.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody().getEmail()).isEqualTo("jwtuser@example.com");
    }

    @Test
    @Order(4)
    void updateMyInfoTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        UserDto update = new UserDto();
        update.setNickname("변경된닉");
        update.setName("변경된이름");
        update.setAge(31);
        update.setGender("FEMALE");

        HttpEntity<UserDto> entity = new HttpEntity<>(update, headers);
        ResponseEntity<Void> res = rest.exchange(baseUrl + "/me", HttpMethod.PUT, entity, Void.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(5)
    void changePasswordTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        Map<String, String> body = Map.of(
                "currentPassword", "test1234!",
                "newPassword", "newpass123!"
        );

        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Void> res = rest.exchange(baseUrl + "/me/password", HttpMethod.PUT, entity, Void.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(6)
    void deleteUserTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> res = rest.exchange(baseUrl + "/me", HttpMethod.DELETE, entity, Void.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}

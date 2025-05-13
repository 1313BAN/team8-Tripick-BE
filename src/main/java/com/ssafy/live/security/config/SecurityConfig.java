package com.ssafy.live.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ssafy.live.security.jwt.JwtAccessDeniedHandler;
import com.ssafy.live.security.jwt.JwtAuthenticationEntryPoint;
import com.ssafy.live.security.jwt.JwtAuthenticationFilter;
import com.ssafy.live.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint entryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	return http
    		    .httpBasic(httpBasic -> httpBasic.disable())
    		    .csrf(csrf -> csrf.disable())
    		    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    		    .authorizeHttpRequests(auth -> auth
    		        //.requestMatchers("/api/users/login", "/api/users/signup").permitAll()
    		        .anyRequest().permitAll()
    		    )
    		    .exceptionHandling(ex -> ex
    		        .authenticationEntryPoint(entryPoint)
    		        .accessDeniedHandler(accessDeniedHandler)
    		    )
    		    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
    		    .build();

    }									

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

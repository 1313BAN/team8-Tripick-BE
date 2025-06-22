package com.ssafy.live.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ssafy.live.security.auth.CustomUserDetailsService;
import com.ssafy.live.security.jwt.JwtAccessDeniedHandler;
import com.ssafy.live.security.jwt.JwtAuthenticationEntryPoint;
import com.ssafy.live.security.jwt.JwtAuthenticationFilter;
import com.ssafy.live.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtTokenProvider jwtTokenProvider;
        private final JwtAuthenticationEntryPoint entryPoint;
        private final JwtAccessDeniedHandler accessDeniedHandler;
        private final CustomUserDetailsService userDetailsService;
        private final StringRedisTemplate redisTemplate;

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정
                                .csrf(csrf -> csrf.disable())
                                .httpBasic(httpBasic -> httpBasic.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/api/auth/login",
                                                                "/api/auth/reissue",
                                                                "/api/users/signup",
                                                                "/api/reviews/**",
                                                                "/api/recommendations/**",
                                                                "/api/ai-recommend/**",
                                                                "/api/spots/**",
                                                                "/api/public/**")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(entryPoint)
                                                .accessDeniedHandler(accessDeniedHandler))
                                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate),
                                                UsernamePasswordAuthenticationFilter.class)
                                .build();
        }

        @Bean
        AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
                AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
                builder.userDetailsService(userDetailsService)
                                .passwordEncoder(passwordEncoder());
                return builder.build();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        /**
         * CORS 설정 (프론트 도메인 허용, 쿠키 포함)
         */
        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of(
                                "http://localhost:5173",
                                "http://localhost:5174",
                                "http://127.0.0.1:5173",
                                "http://127.0.0.1:5500",
                                "https://tripick.store",
                                "https://www.tripick.store"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(true); // 쿠키 허용 (e.g. refreshToken)
                config.setExposedHeaders(List.of("Authorization")); // 필요한 경우 노출할 헤더 지정

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/api/**", config); // API 요청에만 CORS 적용
                return source;
        }
}

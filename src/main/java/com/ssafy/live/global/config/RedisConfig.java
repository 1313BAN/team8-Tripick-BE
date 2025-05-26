package com.ssafy.live.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 설정 클래스
 * - StringRedisTemplate: 기존 JWT 토큰 저장용 (자동 설정으로 생성됨)
 * - RedisTemplate<String, Object>: 캐싱용 객체 저장
 */
@Configuration
public class RedisConfig {

    /**
     * 객체 캐싱용 RedisTemplate 설정
     * 캐시 데이터 (추천 결과 등) 저장에 사용
     */
    @Bean
    public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        
        // Key는 String으로 직렬화
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        
        // Value는 JSON으로 직렬화 (Jackson 사용)
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        
        // 설정 적용
        template.afterPropertiesSet();
        return template;
    }

    /**
     * Jackson ObjectMapper 설정
     * JSON 직렬화/역직렬화 최적화
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
            .registerModule(new JavaTimeModule()) // LocalDateTime 등 처리
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO 8601 포맷 사용
    }
}
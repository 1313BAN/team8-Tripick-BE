// package com.ssafy.live.global.init;

// import com.ssafy.live.domain.spot.service.SpotVectorService;
// import jakarta.annotation.PostConstruct;
// import lombok.RequiredArgsConstructor;

// import org.springframework.context.annotation.Profile;
// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.stereotype.Component;

// @Component
// @RequiredArgsConstructor
// public class SpotVectorInitializer {

//     private final SpotVectorService spotVectorService;
//     private final StringRedisTemplate redisTemplate;

//     private static final String INIT_KEY = "vector:init";

//     @PostConstruct
//     public void init() {
//         Boolean alreadyInitialized = redisTemplate.hasKey(INIT_KEY);

//         if (Boolean.FALSE.equals(alreadyInitialized)) {
//             System.out.println("📌 Redis에 초기 데이터 삽입 시작...");
//             spotVectorService.storeAllToVector();

//             // 삽입 완료 표시
//             redisTemplate.opsForValue().set(INIT_KEY, "true");
//         } else {
//             System.out.println("✅ 이미 초기화 완료됨. 삽입 생략.");
//         }
//     }
// }

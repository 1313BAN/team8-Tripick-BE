package com.ssafy.live.domain.recommendation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.live.domain.recommendation.dto.CategoryRecommendationDTO;
import com.ssafy.live.domain.recommendation.dto.RecommendationRequestDTO;
import com.ssafy.live.domain.recommendation.dto.SpotRecommendationDTO;
import com.ssafy.live.domain.recommendation.service.RecommendationService;
import com.ssafy.live.security.auth.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@RestController 
@RequestMapping("/api/recommendations") 
@RequiredArgsConstructor
@Slf4j
public class RecommendationController {
    
    private final RecommendationService recommendationService;
    private final RedisTemplate<String, Object> objectRedisTemplate; // 캐싱용

    
    /**
     * 1. 성별과 나이에 따른 여행지 추천
     */
    @GetMapping("/by-gender-age")
    public ResponseEntity<List<SpotRecommendationDTO>> getRecommendationsByGenderAndAge(
            @RequestParam String gender,
            @RequestParam Integer minAge, 
            @RequestParam Integer maxAge) {
        
    	
    	// 입력값 검증
    	if (minAge == null || maxAge == null || minAge < 0 || maxAge < 0 || minAge > maxAge) {
    	    throw new IllegalArgumentException("유효하지 않은 나이 범위입니다.");
    	}
    	
    	if (gender == null || gender.trim().isEmpty() || 
    	     (!gender.equals("MALE") && !gender.equals("FEMALE"))) {
    	     throw new IllegalArgumentException("유효하지 않은 성별 값입니다.");
    	}
    	
        List<SpotRecommendationDTO> recommendations = 
            recommendationService.getRecommendationsByGenderAndAge(gender, minAge, maxAge);
        
        return ResponseEntity.ok(recommendations);
    }
    
    /**
     * 2. 여행 동기에 따른 추천
     */
    @GetMapping("/by-motive")
    public ResponseEntity<List<SpotRecommendationDTO>> getRecommendationsByMotive(
            @RequestParam Integer motiveCode) {
        
        List<SpotRecommendationDTO> recommendations = 
            recommendationService.getRecommendationsByMotive(motiveCode);
        
        return ResponseEntity.ok(recommendations);
    }
    
    /**
     * 3. 2022년 가장 인기 있었던 여행지 (평점 기준)
     */
    @GetMapping("/popular-2022")
    public ResponseEntity<List<SpotRecommendationDTO>> getPopularSpotsIn2022() {
        List<SpotRecommendationDTO> popularSpots = 
            recommendationService.getPopularSpotsIn2022();
        
        return ResponseEntity.ok(popularSpots);
    }
    
    @PostMapping("/complex")
    public ResponseEntity<List<SpotRecommendationDTO>> getComplexRecommendations(
            @RequestBody RecommendationRequestDTO requestDTO) {
        
        // 캐시 키 생성
        String cacheKey = "recommendations:" + requestDTO.toString().hashCode();
        log.info("요청 처리 시작 - 캐시 키: {}", cacheKey);
        
        try {
            // Cache Aside Pattern - Read
            Object cachedResult = objectRedisTemplate.opsForValue().get(cacheKey);
            if (cachedResult != null) {
                log.info("✅ 캐시 히트! 캐시된 결과 반환");
                @SuppressWarnings("unchecked")
                List<SpotRecommendationDTO> recommendations = (List<SpotRecommendationDTO>) cachedResult;
                return ResponseEntity.ok(recommendations);
            }
        } catch (Exception e) {
            log.warn("⚠️ 캐시 조회 실패, DB 조회로 진행: {}", e.getMessage());
        }
        
        // Cache Aside Pattern - DB 조회
        log.info("🔍 캐시 미스! DB에서 추천 결과 조회 시작");
        List<SpotRecommendationDTO> recommendations = 
            recommendationService.getComplexRecommendations(requestDTO);
        
        try {
            // Cache Aside Pattern - Write  
            objectRedisTemplate.opsForValue().set(cacheKey, recommendations, Duration.ofMinutes(10));
            log.info("💾 DB 조회 결과를 캐시에 저장 완료 (TTL: 10분, 결과 개수: {}개)", recommendations.size());
        } catch (Exception e) {
            log.warn("⚠️ 캐시 저장 실패 (결과는 정상 반환): {}", e.getMessage());
        }
        
        return ResponseEntity.ok(recommendations);
    }
    
    /**
     * 캐시 관리 API (개발/테스트용)
     */
    @DeleteMapping("/cache")
    public ResponseEntity<String> clearRecommendationCache() {
        try {
            var keys = objectRedisTemplate.keys("recommendations:*");
            if (keys != null && !keys.isEmpty()) {
                objectRedisTemplate.delete(keys);
                log.info("🗑️ 추천 캐시 {} 개 삭제 완료", keys.size());
                return ResponseEntity.ok("캐시 " + keys.size() + "개 삭제 완료");
            } else {
                return ResponseEntity.ok("삭제할 캐시가 없습니다");
            }
        } catch (Exception e) {
            log.error("❌ 캐시 삭제 실패: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("캐시 삭제 실패");
        }
    }
    
    @GetMapping("/cache/status")
    public ResponseEntity<String> getCacheStatus() {
        try {
            var keys = objectRedisTemplate.keys("recommendations:*");
            int count = keys != null ? keys.size() : 0;
            return ResponseEntity.ok("현재 캐시된 추천 결과: " + count + "개");
        } catch (Exception e) {
            return ResponseEntity.ok("Redis 연결 상태 확인 필요");
        }
    }
    
 
    
    /**
     * 6. 컨텐츠 타입별 추천
     */
    @GetMapping("/by-content-type")
    public ResponseEntity<List<SpotRecommendationDTO>> getRecommendationsByContentType(
            @RequestParam Integer contentTypeId) {
        
        List<SpotRecommendationDTO> recommendations = 
            recommendationService.getRecommendationsByContentType(contentTypeId);
        
        return ResponseEntity.ok(recommendations);
    }
    
    
    /**
     * 7. 로그인한 사용자의 성별 및 나이 기반 추천 (카테고리 정보 포함)
     */
    @GetMapping("/my-gender-age")
    public ResponseEntity<CategoryRecommendationDTO> getRecommendationsByMyGenderAndAge(
            @AuthenticationPrincipal CustomUserDetails user) {
    	
    	
        
        CategoryRecommendationDTO recommendations =
            recommendationService.getRecommendationsByUserGenderAndAgeWithCategory(user.getId());
        
        return ResponseEntity.ok(recommendations);
    }

    /**
     * 8. 로그인한 사용자의 여행 동기 기반 추천 (카테고리 정보 포함)
     */
    @GetMapping("/my-motive")
    public ResponseEntity<CategoryRecommendationDTO> getRecommendationsByMyMotive(
            @AuthenticationPrincipal CustomUserDetails user) {
        
        CategoryRecommendationDTO recommendations =
            recommendationService.getRecommendationsByUserMotiveWithCategory(user.getId());
        
        return ResponseEntity.ok(recommendations);
    }
    
}
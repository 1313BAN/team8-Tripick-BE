package com.ssafy.live.domain.recommendation.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.live.domain.recommendation.dto.CategoryRecommendationDTO;
import com.ssafy.live.domain.recommendation.dto.RecommendationRequestDTO;
import com.ssafy.live.domain.recommendation.dto.SpotRecommendationDTO;
import com.ssafy.live.domain.recommendation.service.RecommendationService;
import com.ssafy.live.security.auth.CustomUserDetails;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.Map;

@RestController 
@RequestMapping("/api/recommendations") 
public class RecommendationController {
    
    private final RecommendationService recommendationService;
    
    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }
    
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
    
    /**
     * 4. 복합 조건 기반 추천 (동기, 나이, 성별, 관광지 타입 등)
     */
    @PostMapping("/complex")
    public ResponseEntity<List<SpotRecommendationDTO>> getComplexRecommendations(
            @RequestBody RecommendationRequestDTO requestDTO) {
        
        List<SpotRecommendationDTO> recommendations = 
            recommendationService.getComplexRecommendations(requestDTO);
        
        return ResponseEntity.ok(recommendations);
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
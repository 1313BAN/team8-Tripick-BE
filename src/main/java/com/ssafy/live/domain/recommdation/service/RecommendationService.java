package com.ssafy.live.domain.recommdation.service;

import java.util.List;
import java.util.Map;

import com.ssafy.live.domain.recommdation.dto.CategoryRecommendationDTO;
import com.ssafy.live.domain.recommdation.dto.RecommendationRequestDTO;
import com.ssafy.live.domain.recommdation.dto.SpotRecommendationDTO;

public interface RecommendationService {
    
    /**
     * 성별과 나이에 따른 여행지 추천
     */
    List<SpotRecommendationDTO> getRecommendationsByGenderAndAge(String gender, Integer minAge, Integer maxAge);
    
    /**
     * 여행 동기에 따른 추천
     */
    List<SpotRecommendationDTO> getRecommendationsByMotive(Integer motiveCode);
    
    /**
     * 2022년 가장 인기 있었던 여행지 (평점 기준)
     */
    List<SpotRecommendationDTO> getPopularSpotsIn2022();
    
    /**
     * 복합 조건 기반 추천 (동기, 나이, 성별, 관광지 타입 등)
     */
    List<SpotRecommendationDTO> getComplexRecommendations(RecommendationRequestDTO requestDTO);
    
    /**
     * 특정 지역 기반 추천
     */
    List<SpotRecommendationDTO> getRecommendationsByArea(Integer areaCode, Integer siGunGuCode);
    
    /**
     * 컨텐츠 타입별 추천
     */
    List<SpotRecommendationDTO> getRecommendationsByContentType(Integer contentTypeId);
    
    /**
     * 로그인 사용자의 성별/나이 기반 추천 (카테고리 정보 포함)
     */
    CategoryRecommendationDTO getRecommendationsByUserGenderAndAgeWithCategory(Integer userId);

    /**
     * 8. 로그인한 사용자의 여행 동기 기반 추천 (카테고리 정보 포함)
     */
    CategoryRecommendationDTO getRecommendationsByUserMotiveWithCategory(Integer userId);
    
}
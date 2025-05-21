package com.ssafy.live.domain.recommdation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.live.domain.recommdation.dto.RecommendationRequestDTO;
import com.ssafy.live.domain.recommdation.dto.SpotRecommendationDTO;

@Mapper
public interface RecommendationDao {

    // 성별과 나이에 따른 상위 평점 관광지 추천
    List<SpotRecommendationDTO> selectTopSpotsByGenderAndAge(
            @Param("gender") String gender, 
            @Param("minAge") Integer minAge, 
            @Param("maxAge") Integer maxAge);
    
    // 여행 동기에 따른 상위 평점 관광지 추천
    List<SpotRecommendationDTO> selectTopSpotsByMotive(
            @Param("motiveCode") Integer motiveCode);
    
    // 2022년 가장 인기 있었던 관광지 추천
    List<SpotRecommendationDTO> selectPopularSpotsIn2022();
    
    // 복합 조건에 따른 관광지 추천
    List<SpotRecommendationDTO> selectSpotsByMultipleCriteria(
            @Param("criteria") RecommendationRequestDTO criteria);
    
    // 특정 지역 기반 관광지 추천
    List<SpotRecommendationDTO> selectTopSpotsByArea(
            @Param("areaCode") Integer areaCode, 
            @Param("siGunGuCode") Integer siGunGuCode);
    
    // 컨텐츠 타입별 관광지 추천
    List<SpotRecommendationDTO> selectTopSpotsByContentType(
            @Param("contentTypeId") Integer contentTypeId);
    
    // 사용자의 여행 동기 목록 조회
    List<Integer> selectUserMotives(@Param("userId") Integer userId);
    
    // 여행 동기 이름 조회
    String selectMotiveName(@Param("motiveCode") Integer motiveCode);
    
    // 시도 이름 조회
    String selectSidoName(@Param("sidoCode") Integer sidoCode);
}
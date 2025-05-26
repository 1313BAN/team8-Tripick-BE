package com.ssafy.live.domain.recommendation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.live.domain.recommendation.dto.RecommendationRequestDTO;
import com.ssafy.live.domain.recommendation.dto.SpotRecommendationDTO;

@Mapper
public interface RecommendationDao {

	/**
	* 성별과 나이에 따른 상위 평점 관광지 추천
	* @param gender 성별 (M:남성, F:여성)
	* @param minAge 최소 나이
	* @param maxAge 최대 나이
	* @return 추천 관광지 목록, 결과가 없을 경우 빈 리스트 반환
	*/
    List<SpotRecommendationDTO> selectTopSpotsByGenderAndAge(
            @Param("gender") String gender, 
            @Param("minAge") Integer minAge, 
            @Param("maxAge") Integer maxAge);
    
    /**
    * 여행 동기에 따른 상위 평점 관광지 추천
    * @param motiveCode 여행 동기 코드
    * @return 추천 관광지 목록, 결과가 없을 경우 빈 리스트 반환
    */
    List<SpotRecommendationDTO> selectTopSpotsByMotive(
            @Param("motiveCode") Integer motiveCode);
    
    /**
    * 특정 연도에 가장 인기 있었던 관광지 추천
    * @param year 연도
    * @return 추천 관광지 목록, 결과가 없을 경우 빈 리스트 반환
    */
    List<SpotRecommendationDTO> selectPopularSpotsByYear(
    @Param("year") Integer year);
    
    /**
    * 복합 조건에 따른 관광지 추천
    * @param criteria 추천 조건
    * @return 추천 관광지 목록, 결과가 없을 경우 빈 리스트 반환
    */
    List<SpotRecommendationDTO> selectSpotsByMultipleCriteria(
            @Param("criteria") RecommendationRequestDTO criteria);
    
    /**
    * 특정 지역 기반 관광지 추천
    * @param areaCode 지역 코드
    * @param siGunGuCode 시군구 코드 (옵션)
    * @return 추천 관광지 목록, 결과가 없을 경우 빈 리스트 반환
    */
    List<SpotRecommendationDTO> selectTopSpotsByArea(
            @Param("areaCode") Integer areaCode, 
            @Param("siGunGuCode") Integer siGunGuCode);
    
    /**
    * 컨텐츠 타입별 관광지 추천
    * @param contentTypeId 컨텐츠 타입 ID
    * @return 추천 관광지 목록, 결과가 없을 경우 빈 리스트 반환
    */
    List<SpotRecommendationDTO> selectTopSpotsByContentType(
            @Param("contentTypeId") Integer contentTypeId);
    
    /**
    * 사용자의 여행 동기 목록 조회
    * @param userId 사용자 ID
    * @return 여행 동기 코드 목록, 결과가 없을 경우 빈 리스트 반환
    */
    List<Integer> selectUserMotives(@Param("userId") Integer userId);
    
    /**
    * 여행 동기 이름 조회
    * @param motiveCode 여행 동기 코드
    * @return 여행 동기 이름, 존재하지 않는 코드인 경우 null 반환
    */
    String selectMotiveName(@Param("motiveCode") Integer motiveCode);
    
    /**
    * 시도 이름 조회
    * @param sidoCode 시도 코드
    * @return 시도 이름, 존재하지 않는 코드인 경우 null 반환
    */
    String selectSidoName(@Param("sidoCode") Integer sidoCode);
}
package com.ssafy.live.domain.spot.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.live.domain.spot.dto.AgeRatingDto;
import com.ssafy.live.domain.spot.dto.BasicSpotResponseDto;
import com.ssafy.live.domain.spot.dto.SpotVectorDto;

@Mapper
public interface SpotDao {

    /**
     * 특정 관광지의 기본 정보 조회 (평균평점, 리뷰수 포함)
     */
    BasicSpotResponseDto selectBasicSpotByNo(int no);

    /**
     * 특정 관광지의 연령대별 평점 조회
     */
    AgeRatingDto selectAgeRatingsByNo(int no);

    /**
     * 특정 관광지에서 가장 인기있는 동행타입 조회
     */
    String selectMostPopularAccompanyTypeByNo(int no);

    /**
     * 특정 관광지에서 가장 인기있는 모티브 조회
     */
    String selectMostPopularMotiveByNo(int no);

    /**
     * 관광지 삭제
     */
    int deleteSpot(int no);

    List<SpotVectorDto> selectAllForVector();

    /**
     * 경계 내 관광지 기본 정보 조회 (평균평점, 리뷰수 포함)
     */
    List<BasicSpotResponseDto> selectBasicSpotsInBoundary(
            @Param("swLat") double swLat,
            @Param("swLng") double swLng,
            @Param("neLat") double neLat,
            @Param("neLng") double neLng,
            @Param("type") Integer type);

    /**
     * 관광지 검색 (이름, 주소 기반)
     */
    List<BasicSpotResponseDto> searchSpots(
            @Param("keyword") String keyword,
            @Param("type") Integer type);
}

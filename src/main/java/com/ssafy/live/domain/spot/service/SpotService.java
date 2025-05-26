package com.ssafy.live.domain.spot.service;

import java.util.List;

import com.ssafy.live.domain.spot.dto.BasicSpotResponseDto;
import com.ssafy.live.domain.spot.dto.DetailSpotResponseDto;

public interface SpotService {
    
    /**
     * 특정 관광지의 기본 정보 조회 (평균평점, 리뷰수 포함)
     * @param no 관광지 번호
     * @return 기본 관광지 정보
     */
    BasicSpotResponseDto getBasicSpotByNo(int no);
    
    /**
     * 특정 관광지의 상세 정보 조회 (연령대별 평점, 인기 동행타입, 인기 모티브 포함)
     * @param no 관광지 번호
     * @return 상세 관광지 정보
     */
    DetailSpotResponseDto getDetailSpotByNo(int no);
    
    /**
     * 관광지 삭제
     * @param no 관광지 번호
     */
    void deleteSpot(int no);
    
    /**
     * 좌표 경계 내의 관광지 기본 정보 조회 (평균평점, 리뷰수 포함)
     * @param swLat 남서쪽 위도
     * @param swLng 남서쪽 경도
     * @param neLat 북동쪽 위도
     * @param neLng 북동쪽 경도
     * @param type 관광지 타입 (선택사항)
     * @return 경계 내 관광지 기본 정보 리스트
     */
    List<BasicSpotResponseDto> getBasicSpotsInBoundary(double swLat, double swLng, double neLat, double neLng, Integer type);


    /**
     * 관광지 검색 (이름, 주소 기반)
     */
    List<BasicSpotResponseDto> searchSpots(String keyword, Integer type);

}
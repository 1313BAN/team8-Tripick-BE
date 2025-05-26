package com.ssafy.live.domain.spot.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.live.domain.spot.dao.SpotDao;
import com.ssafy.live.domain.spot.dto.AgeRatingDto;
import com.ssafy.live.domain.spot.dto.BasicSpotResponseDto;
import com.ssafy.live.domain.spot.dto.DetailSpotResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService { 
    
    private final SpotDao spotDao;
    
    /**
     * 특정 관광지의 기본 정보 조회 (평균평점, 리뷰수 포함)
     */
    @Override
    @Transactional(readOnly = true)
    public BasicSpotResponseDto getBasicSpotByNo(int no) {
        BasicSpotResponseDto spot = spotDao.selectBasicSpotByNo(no);
        if (spot == null) {
            throw new NoSuchElementException("관광지를 찾을 수 없습니다. no: " + no);
        }
        return spot;
    }
    
    /**
     * 특정 관광지의 상세 정보 조회 (연령대별 평점, 인기 동행타입, 인기 모티브 포함)
     */
    @Override
    @Transactional(readOnly = true)
    public DetailSpotResponseDto getDetailSpotByNo(int no) {
        // 기본 정보 조회
        BasicSpotResponseDto basicSpot = getBasicSpotByNo(no);
        
        // 연령대별 평점 조회
        AgeRatingDto ageRatings = spotDao.selectAgeRatingsByNo(no);
        
        // 가장 인기있는 동행타입 조회
        String mostPopularAccompanyType = spotDao.selectMostPopularAccompanyTypeByNo(no);
        
        // 가장 인기있는 모티브 조회
        String mostPopularMotive = spotDao.selectMostPopularMotiveByNo(no);
        
        // DetailSpotResponseDto 생성
        DetailSpotResponseDto detailSpot = new DetailSpotResponseDto(
            basicSpot.getNo(),
            basicSpot.getTitle(),
            basicSpot.getContentTypeId(),
            basicSpot.getLatitude(),
            basicSpot.getLongitude(),
            basicSpot.getAverageRating(),
            basicSpot.getReviewCount(),
            ageRatings,
            mostPopularAccompanyType,
            mostPopularMotive
        );
        
        return detailSpot;
    }
    
    /**
     * 관광지 삭제
     */
    @Override
    @Transactional
    public void deleteSpot(int no) {
        // 존재 여부 확인
        BasicSpotResponseDto spot = spotDao.selectBasicSpotByNo(no);
        if (spot == null) {
            throw new NoSuchElementException("삭제할 관광지를 찾을 수 없습니다. no: " + no);
        }
        spotDao.deleteSpot(no);
    }
    
    /**
     * 좌표 경계 내의 관광지 기본 정보 조회 (평균평점, 리뷰수 포함)
     */
    @Override
    @Transactional(readOnly = true)
    public List<BasicSpotResponseDto> getBasicSpotsInBoundary(double swLat, double swLng, double neLat, double neLng, Integer type) {
        return spotDao.selectBasicSpotsInBoundary(swLat, swLng, neLat, neLng, type);
    }
    
    
    
    /**
     * 관광지 검색 (이름, 주소 기반)
     */
    @Override
    @Transactional(readOnly = true)
    public List<BasicSpotResponseDto> searchSpots(String keyword, Integer type) {
        // 키워드 검사
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색 키워드가 필요합니다.");
        }
        
        return spotDao.searchSpots(keyword.trim(), type);
    }
    
    
    
    
    
    
    
}
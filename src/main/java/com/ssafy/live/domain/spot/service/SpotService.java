package com.ssafy.live.domain.spot.service;

import java.math.BigDecimal;
import java.util.List;

import com.ssafy.live.domain.spot.dto.SpotDto;

public interface SpotService {
    // 모든 관광지 조회
    List<SpotDto> getAllSpots();
    
    // no로 특정 관광지 조회
    SpotDto getSpotByNo(int no);
    
    // 관광지 삭제
    void deleteSpot(int no);
    
    // 새 관광지 생성
    SpotDto createSpot(SpotDto spotDto);
    
    // 이번주 인기 관광지 조회
    List<SpotDto> getTopWeeklySpots();
}

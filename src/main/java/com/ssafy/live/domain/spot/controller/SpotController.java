package com.ssafy.live.domain.spot.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.live.domain.spot.dto.BasicSpotResponseDto;
import com.ssafy.live.domain.spot.dto.DetailSpotResponseDto;
import com.ssafy.live.domain.spot.service.SpotService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spots")
public class SpotController {
    
    private final SpotService spotService;
    
    /**
     * 특정 관광지의 기본 정보 조회 (평균평점, 리뷰수 포함)
     * @param no 관광지 번호
     * @return 기본 관광지 정보
     */
    @GetMapping("/{no}")
    public ResponseEntity<BasicSpotResponseDto> getBasicSpotByNo(@PathVariable int no) {
        BasicSpotResponseDto spot = spotService.getBasicSpotByNo(no);
        return new ResponseEntity<>(spot, HttpStatus.OK);
    }
    
    /**
     * 특정 관광지의 상세 정보 조회 (연령대별 평점, 인기 동행타입, 인기 모티브 포함)
     * @param no 관광지 번호
     * @return 상세 관광지 정보
     */
    @GetMapping("/{no}/detail")
    public ResponseEntity<DetailSpotResponseDto> getDetailSpotByNo(@PathVariable int no) {
        DetailSpotResponseDto spot = spotService.getDetailSpotByNo(no);
        return new ResponseEntity<>(spot, HttpStatus.OK);
    }
    
    /**
     * 관광지 삭제
     * @param no 관광지 번호
     */
    @DeleteMapping("/{no}")
    public ResponseEntity<Void> deleteSpot(@PathVariable int no) {
        spotService.deleteSpot(no);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    /**
     * 좌표 경계 내의 관광지 기본 정보 조회 (평균평점, 리뷰수 포함)
     * @param swLat 남서쪽 위도
     * @param swLng 남서쪽 경도  
     * @param neLat 북동쪽 위도
     * @param neLng 북동쪽 경도
     * @param type 관광지 타입 (선택사항)
     * @return 경계 내 관광지 기본 정보 리스트
     */
    @GetMapping("/in-boundary")
    public ResponseEntity<List<BasicSpotResponseDto>> getSpotsInBoundary(
            @RequestParam double swLat,
            @RequestParam double swLng,
            @RequestParam double neLat,
            @RequestParam double neLng,
            @RequestParam(required = false) Integer type  // content_type_id
    ) {
        List<BasicSpotResponseDto> spots = spotService.getBasicSpotsInBoundary(swLat, swLng, neLat, neLng, type);
        return ResponseEntity.ok(spots);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<BasicSpotResponseDto>> searchSpots(
    		@RequestParam String keyword,
            @RequestParam(required = false) Integer type

		){
        List<BasicSpotResponseDto> spots = spotService.searchSpots(
                keyword, type);
            return ResponseEntity.ok(spots);
        }
}
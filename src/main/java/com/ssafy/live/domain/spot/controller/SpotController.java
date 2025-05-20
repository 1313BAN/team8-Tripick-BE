package com.ssafy.live.domain.spot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ssafy.live.domain.spot.dto.SpotDto;
import com.ssafy.live.domain.spot.service.SpotService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spots")
public class SpotController {
	
	private final SpotService spotService;
	
	
    // 모든 관광지 조회
    @GetMapping
    public ResponseEntity<List<SpotDto>> getAllSpots() {
        List<SpotDto> spots = spotService.getAllSpots();
        return new ResponseEntity<>(spots, HttpStatus.OK);
    }
    

    // ID로 특정 관광지 조회
    @GetMapping("/{no}")
    public ResponseEntity<SpotDto> getSpotByNo(@PathVariable int no) {
        SpotDto spot = spotService.getSpotByNo(no);
        return new ResponseEntity<>(spot, HttpStatus.OK);
    }
    
    // 관광지 삭제
    @DeleteMapping("/{no}")
    public ResponseEntity<Void> deleteSpot(@PathVariable int no) {
        spotService.deleteSpot(no);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    // 새 관광지 생성
    @PostMapping
    public ResponseEntity<SpotDto> createSpot(@RequestBody SpotDto spotDto) {
        SpotDto createdSpot = spotService.insertSpot(spotDto);
        return new ResponseEntity<>(createdSpot, HttpStatus.CREATED);
    }
    
    // 좌표 주변의 관광지 가져오기
	@GetMapping("/in-boundary")
	public ResponseEntity<List<SpotDto>> getSpotsInBoundary(
	        @RequestParam double swLat,
	        @RequestParam double swLng,
	        @RequestParam double neLat,
	        @RequestParam double neLng,
	        @RequestParam(required = false) Integer type  // content_type_id
	) {
	    List<SpotDto> spots = spotService.getSpotsInBoundary(swLat, swLng, neLat, neLng, type);
	    return ResponseEntity.ok(spots);
	}
	
	
}

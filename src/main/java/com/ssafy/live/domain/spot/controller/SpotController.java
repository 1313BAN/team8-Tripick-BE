package com.ssafy.live.domain.spot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.live.domain.spot.dto.SpotDto;
import com.ssafy.live.domain.spot.service.SpotService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spots")
public class SpotController {
	
	private final SpotService spotService;
	
	@GetMapping("/top-weekly/")
	public ResponseEntity<?> receiveSpot() {
		
		
		
	     try {
		 List<SpotDto> topSpots = spotService.getTopWeeklySpots();
		
		 System.out.println(topSpots);
		 
		 
		 Map<String, Object> response = new HashMap<>();
         response.put("success", true);
         response.put("message", "이번주 인기 관광지 조회 성공");
         response.put("data", topSpots);
		
         return ResponseEntity.ok(response);
         
	    } catch (Exception e) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("success", false);
	        errorResponse.put("message", "이번주 인기 관광지 조회 실패: " + e.getMessage());
	        
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
}

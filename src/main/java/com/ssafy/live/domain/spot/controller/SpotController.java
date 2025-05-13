package com.ssafy.live.domain.spot.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.live.domain.spot.dto.SpotDto;
import com.ssafy.live.domain.spot.service.SpotService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spots")
public class SpotController {
	
	private final SpotService spotService;
	
	
    /**
     * Retrieves a list of all tourist spots.
     *
     * @return a ResponseEntity containing a list of SpotDto objects and HTTP 200 OK status
     */
    @GetMapping
    public ResponseEntity<List<SpotDto>> getAllSpots() {
        List<SpotDto> spots = spotService.getAllSpots();
        return new ResponseEntity<>(spots, HttpStatus.OK);
    }
    

    /**
     * Retrieves a tourist spot by its unique identifier.
     *
     * @param no the unique identifier of the tourist spot
     * @return the tourist spot data with HTTP 200 OK if found
     */
    @GetMapping("/{no}")
    public ResponseEntity<SpotDto> getSpotByNo(@PathVariable int no) {
        SpotDto spot = spotService.getSpotByNo(no);
        return new ResponseEntity<>(spot, HttpStatus.OK);
    }
    
    /**
     * Deletes the tourist spot identified by the given ID.
     *
     * @param no the unique identifier of the tourist spot to delete
     * @return HTTP 204 No Content if the deletion is successful
     */
    @DeleteMapping("/{no}")
    public ResponseEntity<Void> deleteSpot(@PathVariable int no) {
        spotService.deleteSpot(no);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    /**
     * Creates a new tourist spot using the provided data.
     *
     * @param spotDto the data for the new tourist spot
     * @return the created tourist spot with HTTP 201 Created status
     */
    @PostMapping
    public ResponseEntity<SpotDto> createSpot(@RequestBody SpotDto spotDto) {
        SpotDto createdSpot = spotService.createSpot(spotDto);
        return new ResponseEntity<>(createdSpot, HttpStatus.CREATED);
    }
    
    
	/**
	 * Retrieves the top weekly popular tourist spots.
	 *
	 * Returns a JSON response containing a success status, message, and a list of the top weekly spots on success,
	 * or an error message with failure status on error.
	 *
	 * @return a ResponseEntity with a JSON body indicating success or failure and the corresponding data or error message
	 */
	@GetMapping("/top-weekly")
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

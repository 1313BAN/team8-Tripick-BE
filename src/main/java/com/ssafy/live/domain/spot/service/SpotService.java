package com.ssafy.live.domain.spot.service;

import java.math.BigDecimal;
import java.util.List;

import com.ssafy.live.domain.spot.dto.SpotDto;

public interface SpotService {
    /**
 * Retrieves a list of all tourist spots.
 *
 * @return a list of all tourist spots as SpotDto objects
 */
    List<SpotDto> getAllSpots();
    
    /****
 * Retrieves a tourist spot by its unique identifier.
 *
 * @param no the unique identifier of the tourist spot
 * @return the corresponding SpotDto if found
 */
    SpotDto getSpotByNo(int no);
    
    /**
 * Deletes a tourist spot identified by its unique number.
 *
 * @param no the unique identifier of the tourist spot to delete
 */
    void deleteSpot(int no);
    
    /**
 * Creates a new tourist spot using the provided data.
 *
 * @param spotDto the data for the tourist spot to be created
 * @return the created tourist spot
 */
    SpotDto createSpot(SpotDto spotDto);
    
    /****
 * Retrieves a list of the most popular tourist spots for the current week.
 *
 * @return a list of SpotDto objects representing the top tourist spots of the week
 */
    List<SpotDto> getTopWeeklySpots();
}

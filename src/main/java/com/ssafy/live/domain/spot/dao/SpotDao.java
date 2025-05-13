package com.ssafy.live.domain.spot.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.live.domain.spot.dto.SpotDto;

@Mapper
public interface SpotDao {
	
    /**
 * Retrieves a list of all spots.
 *
 * @return a list containing all SpotDto objects
 */
List<SpotDto> selectAllSpots();
	
    /****
 * Retrieves a spot record by its unique identifier.
 *
 * @param no the unique identifier of the spot
 * @return the corresponding SpotDto, or null if not found
 */
SpotDto selectSpotByNo(int no);
    
    /****
 * Inserts a new spot record into the database.
 *
 * @param spotDto the spot data to insert
 * @return the number of rows affected by the insert operation
 */
int insertSpot(SpotDto spotDto);
    
    /****
 * Deletes the spot record with the specified identifier.
 *
 * @param no the unique identifier of the spot to delete
 * @return the number of records deleted (typically 1 if successful, 0 if not found)
 */
int deleteSpot(int no);
    
	/**
 * Retrieves a list of the top weekly spots starting from the specified date.
 *
 * @param startDate the date from which the weekly period begins
 * @return a list of top weekly spots
 */
List<SpotDto> selectTopWeeklySpots(Date startDate);
	
}

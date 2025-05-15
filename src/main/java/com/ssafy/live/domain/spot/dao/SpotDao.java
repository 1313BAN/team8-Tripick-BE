package com.ssafy.live.domain.spot.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.live.domain.spot.dto.SpotDto;

@Mapper

public interface SpotDao {
	
    List<SpotDto> selectAllSpots();
    
    SpotDto selectSpotByNo(int no);

    int insertSpot(SpotDto spotDto);

    int deleteSpot(int no);
    
	List<SpotDto> selectTopWeeklySpots(Date startDate);
	
	List<SpotDto> findInBounds(double swLat, double swLng, double neLat, double neLng, Integer type);

}

package com.ssafy.live.domain.spot.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.live.domain.spot.dto.SpotDto;

@Mapper
public interface SpotDao {
	List<SpotDto> selectTopWeeklySpots(Date startDate);
}

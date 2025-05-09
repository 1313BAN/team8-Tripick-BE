package com.ssafy.live.model.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.live.model.dto.SpotDto;

@Mapper
public interface SpotDao {
	List<SpotDto> selectTopWeeklySpots(Date startDate);
}

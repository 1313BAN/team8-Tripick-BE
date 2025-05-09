package com.ssafy.live.model.service;

import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.live.model.dao.SpotDao;
import com.ssafy.live.model.dto.SpotDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpotService {
    
	private final SpotDao spotDao;
	
    public List<SpotDto> getTopWeeklySpots() {
        LocalDateTime startOfWeek = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        Date startDate = Date.from(startOfWeek.atZone(ZoneId.systemDefault()).toInstant());
        
        return spotDao.selectTopWeeklySpots(startDate);
    }
}
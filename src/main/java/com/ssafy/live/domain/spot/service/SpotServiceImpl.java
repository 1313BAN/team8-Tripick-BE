package com.ssafy.live.domain.spot.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.live.domain.spot.dao.SpotDao;
import com.ssafy.live.domain.spot.dto.SpotDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {
    
	private final SpotDao spotDao;
	
    @Override
    @Transactional(readOnly = true)
    public List<SpotDto> getAllSpots() {
        return spotDao.selectAllSpots();
    }
    
    @Override
    @Transactional(readOnly = true)
    public SpotDto getSpotByNo(int no) {
        SpotDto spot = spotDao.selectSpotByNo(no);
        if (spot == null) {
            throw new NoSuchElementException("관광지를 찾을 수 없습니다. no: " + no);
        }
        return spot;
    }
    
    @Override
    @Transactional
    public void deleteSpot(int no) {
        // 존재 여부 확인
        SpotDto spot = spotDao.selectSpotByNo(no);
        if (spot == null) {
            throw new NoSuchElementException("삭제할 관광지를 찾을 수 없습니다. no: " + no);
        }
        spotDao.deleteSpot(no);
    }
    
    @Override
    @Transactional
    public SpotDto insertSpot(SpotDto spotDto) {
        // SpotDao를 통해 새 관광지 생성
        int result = spotDao.insertSpot(spotDto);
        
        // 성공 여부 확인 (영향받은 행이 1인지)
        if (result != 1) {
            throw new RuntimeException("관광지 등록에 실패했습니다.");
        }
        
        // 등록한 관광지 정보 반환 (일반적으로 auto-increment된 PK 포함)
        return spotDao.selectSpotByNo(spotDto.getNo());
    }
    
	
    public List<SpotDto> getTopWeeklySpots() {
        LocalDateTime startOfWeek = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        Date startDate = Date.from(startOfWeek.atZone(ZoneId.systemDefault()).toInstant());
        
        return spotDao.selectTopWeeklySpots(startDate);
    }

    
}


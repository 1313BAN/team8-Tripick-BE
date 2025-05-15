package com.ssafy.live.domain.spot.dao;

import com.ssafy.live.domain.spot.dao.SpotDao;
import com.ssafy.live.domain.spot.dto.SpotDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SpotDaoTest {

    @Autowired
    private SpotDao spotDao;
    
    /*
    @Test
    @DisplayName("모든 관광지 조회 테스트")
    void selectAllSpots() {
        // when
        List<SpotDto> spots = spotDao.selectAllSpots();
        
        // then
        assertThat(spots).isNotNull();
        // 데이터가 있다고 가정
        assertThat(spots).isNotEmpty();
    }*/
    
    @Test
    @DisplayName("관광지 번호로 조회 테스트")
    void selectSpotByNo() {
        // given
        int existingNo = 56644; // 데이터베이스에 존재하는 번호라고 가정
        
        // when
        SpotDto spot = spotDao.selectSpotByNo(existingNo);
        
        // then
        assertThat(spot).isNotNull();
        assertThat(spot.getNo()).isEqualTo(existingNo);
    }
    
    @Test
    @DisplayName("존재하지 않는 관광지 번호로 조회 테스트")
    void selectSpotByNoNonExistent() {
        // given
        int nonExistentNo = 0; // 존재하지 않는 번호
        
        // when
        SpotDto spot = spotDao.selectSpotByNo(nonExistentNo);
        
        // then
        assertThat(spot).isNull();
    }
    
    @Test
    @DisplayName("관광지 삭제 테스트")
    void deleteSpot() {
    	
        SpotDto newSpot = new SpotDto();
        newSpot.setContentId(0);
        newSpot.setTitle("테스트 관광지");
        newSpot.setContentTypeId(12);
        newSpot.setAreaCode(1);
        newSpot.setSiGunGuCode(23);
        newSpot.setFirstImage1("http://example.com/image1.jpg");
        newSpot.setMapLevel(6);
        newSpot.setLatitude(new BigDecimal("37.551254"));
        newSpot.setLongitude(new BigDecimal("126.988444"));
        
        spotDao.insertSpot(newSpot);
        
        // when
        int result = spotDao.deleteSpot(newSpot.getNo());
        
        // then
        assertThat(result).isEqualTo(1); // 1행이 삭제되었는지 확인
        assertThat(spotDao.selectSpotByNo(newSpot.getNo())).isNull(); // 삭제 후 존재하지 않는지 확인
    }
    
    @Test
    @DisplayName("이번주 인기 관광지 조회 테스트")
    void selectTopWeeklySpots() {
        // given
        Date startDate = new Date(System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)); // 일주일 전
        
        // when
        List<SpotDto> topSpots = spotDao.selectTopWeeklySpots(startDate);
        
        // then
        assertThat(topSpots).isNotNull();
        // 데이터가 존재하지 않을 수도 있으므로 크기 체크는 하지 않음
    }

    
    @Test
    @DisplayName("관광지 등록 테스트")
    void insertSpot() {
        // given
        SpotDto newSpot = new SpotDto();
        newSpot.setContentId(0);
        newSpot.setTitle("테스트 관광지");
        newSpot.setContentTypeId(12);
        newSpot.setAreaCode(1);
        newSpot.setSiGunGuCode(23);
        newSpot.setFirstImage1("http://example.com/image1.jpg");
        newSpot.setMapLevel(6);
        newSpot.setLatitude(new BigDecimal("37.551254"));
        newSpot.setLongitude(new BigDecimal("126.988444"));
        
        // when
        int result = spotDao.insertSpot(newSpot);
        
        // then
        assertThat(result).isEqualTo(1); // 1행이 영향을 받았는지 확인
        
        // 삽입된 관광지 조회
        SpotDto insertedSpot = spotDao.selectSpotByNo(newSpot.getNo());
        assertThat(insertedSpot).isNotNull();
        assertThat(insertedSpot.getTitle()).isEqualTo("테스트 관광지");
    }
    

    
    
    
}
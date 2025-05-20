package com.ssafy.live.domain.spot.dao;

import com.ssafy.live.domain.spot.dao.SpotDao;
import com.ssafy.live.domain.spot.dto.SpotDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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
        int existingNo = 1; // 데이터베이스에 존재하는 번호라고 가정
        
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
        // given - 새 관광지 생성
        SpotDto newSpot = new SpotDto();
        newSpot.setTitle("테스트 관광지");
        newSpot.setContentTypeId(12);
        newSpot.setAreaCode(11);
        newSpot.setSiGunGuCode(110);
        newSpot.setFirstImage1("http://example.com/image1.jpg");
        newSpot.setLatitude(37.551254); // BigDecimal에서 double로 변경
        newSpot.setLongitude(126.988444); // BigDecimal에서 double로 변경
        newSpot.setAddr("서울특별시 중구 세종대로 110"); // addr1, addr2 대신 addr 사용
        
        spotDao.insertSpot(newSpot);
        
        // when
        int result = spotDao.deleteSpot(newSpot.getNo());
        
        // then
        assertThat(result).isEqualTo(1); // 1행이 삭제되었는지 확인
        assertThat(spotDao.selectSpotByNo(newSpot.getNo())).isNull(); // 삭제 후 존재하지 않는지 확인
    }
    
    
    @Test
    @DisplayName("관광지 등록 테스트")
    void insertSpot() {
        // given
        SpotDto newSpot = new SpotDto();
        newSpot.setTitle("테스트 관광지");
        newSpot.setContentTypeId(12);
        newSpot.setAreaCode(11);
        newSpot.setSiGunGuCode(110);
        newSpot.setFirstImage1("http://example.com/image1.jpg");
        newSpot.setLatitude(37.551254); // BigDecimal에서 double로 변경
        newSpot.setLongitude(126.988444); // BigDecimal에서 double로 변경
        newSpot.setAddr("서울특별시 중구 세종대로 110"); // addr 추가
        newSpot.setHomepage("http://example.com");
        newSpot.setOverview("테스트 관광지 설명입니다.");
        
        // when
        int result = spotDao.insertSpot(newSpot);
        
        // then
        assertThat(result).isEqualTo(1); // 1행이 영향을 받았는지 확인
        
        // 삽입된 관광지 조회
        SpotDto insertedSpot = spotDao.selectSpotByNo(newSpot.getNo());
        assertThat(insertedSpot).isNotNull();
        assertThat(insertedSpot.getTitle()).isEqualTo("테스트 관광지");
        assertThat(insertedSpot.getAddr()).isEqualTo("서울특별시 중구 세종대로 110");
    }
    
    @Test
    @DisplayName("범위 내 관광지 검색 테스트")
    void findInBounds() {
        // given
        double swLat = 37.0;
        double swLng = 126.0;
        double neLat = 38.0;
        double neLng = 127.0;
        Integer type = 12; // 관광타입 (옵션)
        
        // when
        List<SpotDto> spotsInBounds = spotDao.findInBounds(swLat, swLng, neLat, neLng, type);
        
        // then
        assertThat(spotsInBounds).isNotNull();
        // 범위 내 모든 관광지의 위치가 실제로 범위 내에 있는지 확인
        spotsInBounds.forEach(spot -> {
            assertThat(spot.getLatitude()).isBetween(swLat, neLat);
            assertThat(spot.getLongitude()).isBetween(swLng, neLng);
            if (type != null) {
                assertThat(spot.getContentTypeId()).isEqualTo(type);
            }
        });
    }
}
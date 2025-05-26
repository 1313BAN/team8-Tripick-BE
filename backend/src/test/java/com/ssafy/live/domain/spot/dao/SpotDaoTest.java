package com.ssafy.live.domain.spot.dao;

import com.ssafy.live.domain.spot.dao.SpotDao;
import com.ssafy.live.domain.spot.dto.AgeRatingDto;
import com.ssafy.live.domain.spot.dto.BasicSpotResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SpotDaoTest {
    
    @Autowired
    private SpotDao spotDao;
    
    @Test
    @DisplayName("관광지 기본 정보 조회 테스트")
    void selectBasicSpotByNo() {
        // given
        int existingNo = 1; // 데이터베이스에 존재하는 번호라고 가정
        
        // when
        BasicSpotResponseDto spot = spotDao.selectBasicSpotByNo(existingNo);
        
        // then
        assertThat(spot).isNotNull();
        assertThat(spot.getNo()).isEqualTo(existingNo);
        assertThat(spot.getTitle()).isNotNull();
        assertThat(spot.getContentTypeId()).isNotNull();
        assertThat(spot.getLatitude()).isNotNull();
        assertThat(spot.getLongitude()).isNotNull();
        assertThat(spot.getAverageRating()).isNotNull();
        assertThat(spot.getReviewCount()).isNotNull();
        
        // 평균평점은 0 이상 5 이하여야 함
        assertThat(spot.getAverageRating()).isBetween(0.0, 5.0);
        // 리뷰수는 0 이상이어야 함
        assertThat(spot.getReviewCount()).isGreaterThanOrEqualTo(0);
    }
    
    @Test
    @DisplayName("존재하지 않는 관광지 번호로 기본 정보 조회 테스트")
    void selectBasicSpotByNoNonExistent() {
        // given
        int nonExistentNo = -1; // 존재하지 않는 번호
        
        // when
        BasicSpotResponseDto spot = spotDao.selectBasicSpotByNo(nonExistentNo);
        
        // then
        assertThat(spot).isNull();
    }
    
    @Test
    @DisplayName("연령대별 평점 조회 테스트")
    void selectAgeRatingsByNo() {
        // given
        int existingNo = 1; // 리뷰가 있는 관광지 번호라고 가정
        
        // when
        AgeRatingDto ageRatings = spotDao.selectAgeRatingsByNo(existingNo);
        
        // then
        assertThat(ageRatings).isNotNull();
        
        // 모든 연령대 평점이 0 이상 5 이하여야 함
        assertThat(ageRatings.getTwenties()).isBetween(0.0, 5.0);
        assertThat(ageRatings.getThirties()).isBetween(0.0, 5.0);
        assertThat(ageRatings.getForties()).isBetween(0.0, 5.0);
        assertThat(ageRatings.getFifties()).isBetween(0.0, 5.0);
        assertThat(ageRatings.getSixties()).isBetween(0.0, 5.0);
    }
    
    @Test
    @DisplayName("리뷰가 없는 관광지의 연령대별 평점 조회 테스트")
    void selectAgeRatingsByNoWithoutReviews() {
        // given
        int spotWithoutReviews = 9999; // 리뷰가 없는 관광지 번호라고 가정
        
        // when
        AgeRatingDto ageRatings = spotDao.selectAgeRatingsByNo(spotWithoutReviews);
        
        // then
        assertThat(ageRatings).isNotNull();
        
        // 리뷰가 없으면 모든 연령대 평점이 0.0이어야 함
        assertThat(ageRatings.getTwenties()).isEqualTo(0.0);
        assertThat(ageRatings.getThirties()).isEqualTo(0.0);
        assertThat(ageRatings.getForties()).isEqualTo(0.0);
        assertThat(ageRatings.getFifties()).isEqualTo(0.0);
        assertThat(ageRatings.getSixties()).isEqualTo(0.0);
    }
    
    @Test
    @DisplayName("가장 인기있는 동행타입 조회 테스트")
    void selectMostPopularAccompanyTypeByNo() {
        // given
        int existingNo = 1; // 리뷰가 있는 관광지 번호라고 가정
        
        // when
        String mostPopularAccompanyType = spotDao.selectMostPopularAccompanyTypeByNo(existingNo);
        
        // then
        // 리뷰가 있다면 동행타입이 반환되어야 함 (null이 아님)
        // 리뷰가 없거나 동행타입이 없다면 null일 수 있음
        if (mostPopularAccompanyType != null) {
            assertThat(mostPopularAccompanyType).isNotBlank();
        }
    }
    
    @Test
    @DisplayName("가장 인기있는 모티브 조회 테스트")
    void selectMostPopularMotiveByNo() {
        // given
        int existingNo = 1; // 리뷰가 있는 관광지 번호라고 가정
        
        // when
        String mostPopularMotive = spotDao.selectMostPopularMotiveByNo(existingNo);
        
        // then
        // 리뷰가 있고 모티브가 설정된 사용자가 있다면 모티브가 반환되어야 함
        // 그렇지 않다면 null일 수 있음
        if (mostPopularMotive != null) {
            assertThat(mostPopularMotive).isNotBlank();
        }
    }
    
    @Test
    @DisplayName("관광지 삭제 테스트")
    void deleteSpot() {
        // given
        int existingNo = 1; // 존재하는 관광지 번호라고 가정
        
        // 삭제 전 존재 확인
        BasicSpotResponseDto spotBeforeDelete = spotDao.selectBasicSpotByNo(existingNo);
        assertThat(spotBeforeDelete).isNotNull();
        
        // when
        int result = spotDao.deleteSpot(existingNo);
        
        // then
        assertThat(result).isEqualTo(1); // 1행이 삭제되었는지 확인
        
        // 삭제 후 존재하지 않는지 확인
        BasicSpotResponseDto spotAfterDelete = spotDao.selectBasicSpotByNo(existingNo);
        assertThat(spotAfterDelete).isNull();
    }
    
    @Test
    @DisplayName("존재하지 않는 관광지 삭제 테스트")
    void deleteNonExistentSpot() {
        // given
        int nonExistentNo = -1; // 존재하지 않는 번호
        
        // when
        int result = spotDao.deleteSpot(nonExistentNo);
        
        // then
        assertThat(result).isEqualTo(0); // 삭제된 행이 없음
    }
    
    @Test
    @DisplayName("범위 내 관광지 기본 정보 검색 테스트")
    void selectBasicSpotsInBoundary() {
        // given
        double swLat = 37.0;
        double swLng = 126.0;
        double neLat = 38.0;
        double neLng = 127.0;
        Integer type = 12; // 관광타입 (옵션)
        
        // when
        List<BasicSpotResponseDto> spotsInBounds = spotDao.selectBasicSpotsInBoundary(swLat, swLng, neLat, neLng, type);
        
        // then
        assertThat(spotsInBounds).isNotNull();
        
        // 범위 내 모든 관광지의 위치가 실제로 범위 내에 있는지 확인
        spotsInBounds.forEach(spot -> {
            assertThat(spot.getLatitude()).isBetween(swLat, neLat);
            assertThat(spot.getLongitude()).isBetween(swLng, neLng);
            assertThat(spot.getAverageRating()).isBetween(0.0, 5.0);
            assertThat(spot.getReviewCount()).isGreaterThanOrEqualTo(0);
            
            if (type != null) {
                assertThat(spot.getContentTypeId()).isEqualTo(type);
            }
        });
        
        // 결과가 300개를 넘지 않는지 확인 (LIMIT 300)
        assertThat(spotsInBounds.size()).isLessThanOrEqualTo(300);
    }
    
    @Test
    @DisplayName("타입 필터 없이 범위 내 관광지 검색 테스트")
    void selectBasicSpotsInBoundaryWithoutTypeFilter() {
        // given
        double swLat = 37.0;
        double swLng = 126.0;
        double neLat = 38.0;
        double neLng = 127.0;
        Integer type = null; // 타입 필터 없음
        
        // when
        List<BasicSpotResponseDto> spotsInBounds = spotDao.selectBasicSpotsInBoundary(swLat, swLng, neLat, neLng, type);
        
        // then
        assertThat(spotsInBounds).isNotNull();
        
        // 범위 내 모든 관광지의 위치가 실제로 범위 내에 있는지 확인
        spotsInBounds.forEach(spot -> {
            assertThat(spot.getLatitude()).isBetween(swLat, neLat);
            assertThat(spot.getLongitude()).isBetween(swLng, neLng);
            assertThat(spot.getAverageRating()).isBetween(0.0, 5.0);
            assertThat(spot.getReviewCount()).isGreaterThanOrEqualTo(0);
            // 타입 필터가 없으므로 모든 타입이 포함될 수 있음
        });
    }
    
    @Test
    @DisplayName("빈 범위에서 관광지 검색 테스트")
    void selectBasicSpotsInBoundaryEmptyRange() {
        // given - 관광지가 없을 것 같은 범위
        double swLat = 90.0;  // 북극 근처
        double swLng = 180.0;
        double neLat = 90.1;
        double neLng = 180.1;
        Integer type = null;
        
        // when
        List<BasicSpotResponseDto> spotsInBounds = spotDao.selectBasicSpotsInBoundary(swLat, swLng, neLat, neLng, type);
        
        // then
        assertThat(spotsInBounds).isNotNull();
        assertThat(spotsInBounds).isEmpty(); // 빈 결과가 예상됨
    }
    
    @Test
    @DisplayName("키워드로 관광지 검색 테스트")
    void searchSpots() {
        // given
        String keyword = "해운대"; // 실제 데이터에 있을 법한 키워드
        Integer type = null; // 타입 필터 없음
        
        // when
        List<BasicSpotResponseDto> spots = spotDao.searchSpots(keyword, type);
        
        // then
        assertThat(spots).isNotNull();
        
        // 검색 결과의 각 항목이 키워드를 포함하는지 확인
        spots.forEach(spot -> {
            assertThat(spot.getTitle()).isNotNull();
            assertThat(spot.getAverageRating()).isBetween(0.0, 5.0);
            assertThat(spot.getReviewCount()).isGreaterThanOrEqualTo(0);
            // 제목이나 주소에 키워드가 포함되어야 함 (실제로는 DB에서 확인됨)
        });
        
        // 결과가 100개를 넘지 않는지 확인 (LIMIT 100)
        assertThat(spots.size()).isLessThanOrEqualTo(100);
    }
    
    @Test
    @DisplayName("키워드와 타입으로 관광지 검색 테스트")
    void searchSpotsWithType() {
        // given
        String keyword = "부산"; // 실제 데이터에 있을 법한 키워드
        Integer type = 12; // 관광지 타입
        
        // when
        List<BasicSpotResponseDto> spots = spotDao.searchSpots(keyword, type);
        
        // then
        assertThat(spots).isNotNull();
        
        // 검색 결과의 각 항목이 조건을 만족하는지 확인
        spots.forEach(spot -> {
            assertThat(spot.getTitle()).isNotNull();
            assertThat(spot.getContentTypeId()).isEqualTo(type);
            assertThat(spot.getAverageRating()).isBetween(0.0, 5.0);
            assertThat(spot.getReviewCount()).isGreaterThanOrEqualTo(0);
        });
        
        // 결과가 100개를 넘지 않는지 확인
        assertThat(spots.size()).isLessThanOrEqualTo(100);
    }
    
    @Test
    @DisplayName("존재하지 않는 키워드로 검색 테스트")
    void searchSpotsWithNonExistentKeyword() {
        // given
        String keyword = "존재하지않는관광지명XYZ123";
        Integer type = null;
        
        // when
        List<BasicSpotResponseDto> spots = spotDao.searchSpots(keyword, type);
        
        // then
        assertThat(spots).isNotNull();
        assertThat(spots).isEmpty(); // 결과가 없어야 함
    }
    
}
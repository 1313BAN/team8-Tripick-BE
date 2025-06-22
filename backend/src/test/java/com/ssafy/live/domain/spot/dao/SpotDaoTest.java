package com.ssafy.live.domain.spot.dao;

import com.ssafy.live.common.AbstractIntegrationTest;
import com.ssafy.live.domain.spot.dto.AgeRatingDto;
import com.ssafy.live.domain.spot.dto.BasicSpotResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 컨테이너/컨텍스트 재사용
@Transactional
@ActiveProfiles("test")
class SpotDaoTest extends AbstractIntegrationTest {

    @Autowired
    private SpotDao spotDao;

    private final int EXISTING_NO = 1;
    private final int NON_EXISTENT_NO = -1;

    @BeforeAll
    void setup() {
        // 필요하면 테스트용 초기 데이터 삽입
    }

    @Test
    @DisplayName("관광지 기본 정보 조회 테스트")
    void selectBasicSpotByNo() {
        BasicSpotResponseDto spot = spotDao.selectBasicSpotByNo(EXISTING_NO);

        assertThat(spot).isNotNull();
        assertThat(spot.getNo()).isEqualTo(EXISTING_NO);
        assertThat(spot.getTitle()).isNotNull();
        assertThat(spot.getContentTypeId()).isNotNull();
        assertThat(spot.getLatitude()).isNotNull();
        assertThat(spot.getLongitude()).isNotNull();
        assertThat(spot.getAverageRating()).isBetween(0.0, 5.0);
        assertThat(spot.getReviewCount()).isGreaterThanOrEqualTo(0);
    }

    @Test
    @DisplayName("존재하지 않는 관광지 번호로 기본 정보 조회 테스트")
    void selectBasicSpotByNoNonExistent() {
        BasicSpotResponseDto spot = spotDao.selectBasicSpotByNo(NON_EXISTENT_NO);
        assertThat(spot).isNull();
    }

    @Test
    @DisplayName("연령대별 평점 조회 테스트")
    void selectAgeRatingsByNo() {
        AgeRatingDto ageRatings = spotDao.selectAgeRatingsByNo(EXISTING_NO);

        assertThat(ageRatings).isNotNull();
        assertThat(ageRatings.getTwenties()).isBetween(0.0, 5.0);
        assertThat(ageRatings.getThirties()).isBetween(0.0, 5.0);
        assertThat(ageRatings.getForties()).isBetween(0.0, 5.0);
        assertThat(ageRatings.getFifties()).isBetween(0.0, 5.0);
        assertThat(ageRatings.getSixties()).isBetween(0.0, 5.0);
    }

    @Test
    @DisplayName("리뷰가 없는 관광지의 연령대별 평점 조회 테스트")
    void selectAgeRatingsByNoWithoutReviews() {
        AgeRatingDto ageRatings = spotDao.selectAgeRatingsByNo(9999);

        assertThat(ageRatings).isNotNull();
        assertThat(ageRatings.getTwenties()).isEqualTo(0.0);
        assertThat(ageRatings.getThirties()).isEqualTo(0.0);
        assertThat(ageRatings.getForties()).isEqualTo(0.0);
        assertThat(ageRatings.getFifties()).isEqualTo(0.0);
        assertThat(ageRatings.getSixties()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("가장 인기있는 동행타입 조회 테스트")
    void selectMostPopularAccompanyTypeByNo() {
        String type = spotDao.selectMostPopularAccompanyTypeByNo(EXISTING_NO);
        if (type != null)
            assertThat(type).isNotBlank();
    }

    @Test
    @DisplayName("가장 인기있는 모티브 조회 테스트")
    void selectMostPopularMotiveByNo() {
        String motive = spotDao.selectMostPopularMotiveByNo(EXISTING_NO);
        if (motive != null)
            assertThat(motive).isNotBlank();
    }

    @Test
    @DisplayName("관광지 삭제 테스트")
    void deleteSpot() {
        BasicSpotResponseDto before = spotDao.selectBasicSpotByNo(EXISTING_NO);
        assertThat(before).isNotNull();

        int result = spotDao.deleteSpot(EXISTING_NO);
        assertThat(result).isEqualTo(1);

        BasicSpotResponseDto after = spotDao.selectBasicSpotByNo(EXISTING_NO);
        assertThat(after).isNull();
    }

    @Test
    @DisplayName("존재하지 않는 관광지 삭제 테스트")
    void deleteNonExistentSpot() {
        int result = spotDao.deleteSpot(NON_EXISTENT_NO);
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("범위 내 관광지 기본 정보 검색 테스트")
    void selectBasicSpotsInBoundary() {
        double swLat = 37.0, swLng = 126.0, neLat = 38.0, neLng = 127.0;
        Integer type = 12;

        List<BasicSpotResponseDto> spots = spotDao.selectBasicSpotsInBoundary(swLat, swLng, neLat, neLng, type);

        assertThat(spots).isNotNull();
        assertThat(spots.size()).isLessThanOrEqualTo(300);

        spots.forEach(spot -> {
            assertThat(spot.getLatitude()).isBetween(swLat, neLat);
            assertThat(spot.getLongitude()).isBetween(swLng, neLng);
            assertThat(spot.getAverageRating()).isBetween(0.0, 5.0);
            assertThat(spot.getReviewCount()).isGreaterThanOrEqualTo(0);
            assertThat(spot.getContentTypeId()).isEqualTo(type);
        });
    }

    @Test
    @DisplayName("타입 필터 없이 범위 내 관광지 검색 테스트")
    void selectBasicSpotsInBoundaryWithoutTypeFilter() {
        double swLat = 37.0, swLng = 126.0, neLat = 38.0, neLng = 127.0;

        List<BasicSpotResponseDto> spots = spotDao.selectBasicSpotsInBoundary(swLat, swLng, neLat, neLng, null);

        assertThat(spots).isNotNull();
        spots.forEach(spot -> {
            assertThat(spot.getLatitude()).isBetween(swLat, neLat);
            assertThat(spot.getLongitude()).isBetween(swLng, neLng);
            assertThat(spot.getAverageRating()).isBetween(0.0, 5.0);
            assertThat(spot.getReviewCount()).isGreaterThanOrEqualTo(0);
        });
    }

    @Test
    @DisplayName("빈 범위에서 관광지 검색 테스트")
    void selectBasicSpotsInBoundaryEmptyRange() {
        List<BasicSpotResponseDto> spots = spotDao.selectBasicSpotsInBoundary(90.0, 180.0, 90.1, 180.1, null);
        assertThat(spots).isNotNull();
        assertThat(spots).isEmpty();
    }

    @Test
    @DisplayName("키워드로 관광지 검색 테스트")
    void searchSpots() {
        List<BasicSpotResponseDto> spots = spotDao.searchSpots("해운대", null);
        assertThat(spots).isNotNull();
        assertThat(spots.size()).isLessThanOrEqualTo(100);
    }

    @Test
    @DisplayName("키워드와 타입으로 관광지 검색 테스트")
    void searchSpotsWithType() {
        List<BasicSpotResponseDto> spots = spotDao.searchSpots("부산", 12);
        assertThat(spots).isNotNull();
        assertThat(spots.size()).isLessThanOrEqualTo(100);
        spots.forEach(spot -> assertThat(spot.getContentTypeId()).isEqualTo(12));
    }

    @Test
    @DisplayName("존재하지 않는 키워드로 검색 테스트")
    void searchSpotsWithNonExistentKeyword() {
        List<BasicSpotResponseDto> spots = spotDao.searchSpots("존재하지않는관광지명XYZ123", null);
        assertThat(spots).isNotNull();
        assertThat(spots).isEmpty();
    }
}

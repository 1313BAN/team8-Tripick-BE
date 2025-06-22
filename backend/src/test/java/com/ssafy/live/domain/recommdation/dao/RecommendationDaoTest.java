package com.ssafy.live.domain.recommdation.dao;

import com.ssafy.live.common.AbstractIntegrationTest;
import com.ssafy.live.domain.recommendation.dao.RecommendationDao;
import com.ssafy.live.domain.recommendation.dto.RecommendationRequestDTO;
import com.ssafy.live.domain.recommendation.dto.SpotRecommendationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class RecommendationDaoTest extends AbstractIntegrationTest {

    @Autowired
    private RecommendationDao recommendationDao;

    @Test
    @DisplayName("성별과 나이에 따른 여행지 추천 테스트")
    void selectTopSpotsByGenderAndAge() {
        // given
        String gender = "남성";
        Integer minAge = 20;
        Integer maxAge = 30;

        // when
        List<SpotRecommendationDTO> recommendations = recommendationDao.selectTopSpotsByGenderAndAge(gender, minAge,
                maxAge);

        // then
        assertThat(recommendations).isNotNull();
        if (!recommendations.isEmpty()) {
            SpotRecommendationDTO firstRecommendation = recommendations.get(0);
            assertThat(firstRecommendation.getNo()).isNotNull();
            assertThat(firstRecommendation.getTitle()).isNotNull();
            assertThat(firstRecommendation.getAvgRating()).isNotNull();
            // 추천 목록이 평점 내림차순으로 정렬되어 있는지 확인
            for (int i = 0; i < recommendations.size() - 1; i++) {
                assertThat(recommendations.get(i).getAvgRating())
                        .isGreaterThanOrEqualTo(recommendations.get(i + 1).getAvgRating());
            }
        }
    }

    @Test
    @DisplayName("여행 동기에 따른 추천 테스트")
    void selectTopSpotsByMotive() {
        // given
        Integer motiveCode = 1; // 예: 1 = 휴양, 2 = 관광 등

        // when
        List<SpotRecommendationDTO> recommendations = recommendationDao.selectTopSpotsByMotive(motiveCode);

        // then
        assertThat(recommendations).isNotNull();
        if (!recommendations.isEmpty()) {
            SpotRecommendationDTO firstRecommendation = recommendations.get(0);
            assertThat(firstRecommendation.getNo()).isNotNull();
            assertThat(firstRecommendation.getTitle()).isNotNull();
            assertThat(firstRecommendation.getAvgRating()).isNotNull();
            // 평점 내림차순 확인
            for (int i = 0; i < recommendations.size() - 1; i++) {
                assertThat(recommendations.get(i).getAvgRating())
                        .isGreaterThanOrEqualTo(recommendations.get(i + 1).getAvgRating());
            }
        }
    }

    @Test
    @DisplayName("2022년 인기 여행지 추천 테스트")
    void selectPopularSpotsIn2022() {
        // when
        List<SpotRecommendationDTO> recommendations = recommendationDao.selectPopularSpotsByYear(2022);

        // then
        assertThat(recommendations).isNotNull();
        if (!recommendations.isEmpty()) {
            SpotRecommendationDTO firstRecommendation = recommendations.get(0);
            assertThat(firstRecommendation.getNo()).isNotNull();
            assertThat(firstRecommendation.getTitle()).isNotNull();
            assertThat(firstRecommendation.getAvgRating()).isNotNull();
            // 평점 내림차순 확인
            for (int i = 0; i < recommendations.size() - 1; i++) {
                assertThat(recommendations.get(i).getAvgRating())
                        .isGreaterThanOrEqualTo(recommendations.get(i + 1).getAvgRating());
            }
        }
    }

    @Test
    @DisplayName("복합 조건 기반 추천 테스트")
    void selectSpotsByMultipleCriteria() {
        // given
        RecommendationRequestDTO criteria = new RecommendationRequestDTO();
        criteria.setGender("여성");
        criteria.setMinAge(25);
        criteria.setMaxAge(40);
        criteria.setMotiveCode(2); // 예시 값
        criteria.setContentTypeId(12); // 예시 값
        criteria.setAreaCode(1); // 예시 값
        criteria.setLimit(5);

        // when
        List<SpotRecommendationDTO> recommendations = recommendationDao.selectSpotsByMultipleCriteria(criteria);

        // then
        assertThat(recommendations).isNotNull();
        if (!recommendations.isEmpty()) {
            assertThat(recommendations.size()).isLessThanOrEqualTo(5); // limit 조건 확인
            SpotRecommendationDTO firstRecommendation = recommendations.get(0);
            assertThat(firstRecommendation.getNo()).isNotNull();
            assertThat(firstRecommendation.getTitle()).isNotNull();
            assertThat(firstRecommendation.getContentTypeId()).isEqualTo(12);
            // 평점 내림차순 확인
            for (int i = 0; i < recommendations.size() - 1; i++) {
                assertThat(recommendations.get(i).getAvgRating())
                        .isGreaterThanOrEqualTo(recommendations.get(i + 1).getAvgRating());
            }
        }
    }

    @Test
    @DisplayName("특정 지역 기반 추천 테스트")
    void selectTopSpotsByArea() {
        // given
        Integer areaCode = 1; // 예: 서울
        Integer siGunGuCode = 1; // 예: 종로구

        // when
        List<SpotRecommendationDTO> recommendations = recommendationDao.selectTopSpotsByArea(areaCode, siGunGuCode);

        // then
        assertThat(recommendations).isNotNull();
        if (!recommendations.isEmpty()) {
            SpotRecommendationDTO firstRecommendation = recommendations.get(0);
            assertThat(firstRecommendation.getNo()).isNotNull();
            assertThat(firstRecommendation.getTitle()).isNotNull();
            // 평점 내림차순 확인
            for (int i = 0; i < recommendations.size() - 1; i++) {
                assertThat(recommendations.get(i).getAvgRating())
                        .isGreaterThanOrEqualTo(recommendations.get(i + 1).getAvgRating());
            }
        }
    }

    @Test
    @DisplayName("지역 코드만으로 기반 추천 테스트 (구군코드 없음)")
    void selectTopSpotsByAreaCodeOnly() {
        // given
        Integer areaCode = 1; // 예: 서울
        Integer siGunGuCode = null;

        // when
        List<SpotRecommendationDTO> recommendations = recommendationDao.selectTopSpotsByArea(areaCode, siGunGuCode);

        // then
        assertThat(recommendations).isNotNull();
        if (!recommendations.isEmpty()) {
            SpotRecommendationDTO firstRecommendation = recommendations.get(0);
            assertThat(firstRecommendation.getNo()).isNotNull();
            assertThat(firstRecommendation.getTitle()).isNotNull();
            // 평점 내림차순 확인
            for (int i = 0; i < recommendations.size() - 1; i++) {
                assertThat(recommendations.get(i).getAvgRating())
                        .isGreaterThanOrEqualTo(recommendations.get(i + 1).getAvgRating());
            }
        }
    }

    @Test
    @DisplayName("컨텐츠 타입별 추천 테스트")
    void selectTopSpotsByContentType() {
        // given
        Integer contentTypeId = 12; // 예: 관광지

        // when
        List<SpotRecommendationDTO> recommendations = recommendationDao.selectTopSpotsByContentType(contentTypeId);

        // then
        assertThat(recommendations).isNotNull();
        if (!recommendations.isEmpty()) {
            SpotRecommendationDTO firstRecommendation = recommendations.get(0);
            assertThat(firstRecommendation.getNo()).isNotNull();
            assertThat(firstRecommendation.getTitle()).isNotNull();
            assertThat(firstRecommendation.getContentTypeId()).isEqualTo(contentTypeId);
            // 평점 내림차순 확인
            for (int i = 0; i < recommendations.size() - 1; i++) {
                assertThat(recommendations.get(i).getAvgRating())
                        .isGreaterThanOrEqualTo(recommendations.get(i + 1).getAvgRating());
            }
        }
    }

    @Test
    @DisplayName("사용자의 여행 동기 목록 조회 테스트")
    void selectUserMotives() {
        // given
        int userId = 1000012; // 테스트 사용자 ID (실제 존재하는 ID로 변경 필요)

        // when
        List<Integer> motives = recommendationDao.selectUserMotives(userId);

        // then
        assertThat(motives).isNotNull();
        // 사용자에게 여행 동기가 있다면 확인
        if (!motives.isEmpty()) {
            Integer firstMotive = motives.get(0);
            assertThat(firstMotive).isNotNull();
            assertThat(firstMotive).isGreaterThan(0); // 동기 코드는 양수여야 함
        }
    }

    @Test
    @DisplayName("여행 동기 이름 조회 테스트")
    void selectMotiveName() {
        // given
        int motiveCode = 1; // 테스트할 동기 코드 (실제 존재하는 코드로 변경 필요)

        // when
        String motiveName = recommendationDao.selectMotiveName(motiveCode);

        // then
        assertThat(motiveName).isNotNull();
        assertThat(motiveName).isNotEmpty();
        // 예상되는 동기 이름이 있다면 확인 (예: "휴양", "관광" 등)
        // assertThat(motiveName).isEqualTo("휴양"); // 실제 데이터베이스 값에 맞게 수정
    }

    @Test
    @DisplayName("시도 이름 조회 테스트")
    void selectSidoName() {
        // given
        int sidoCode = 11; // 테스트할 시도 코드 (실제 존재하는 코드로 변경 필요)

        // when
        String sidoName = recommendationDao.selectSidoName(sidoCode);

        // then
        assertThat(sidoName).isNotNull();
        assertThat(sidoName).isNotEmpty();
        // 예상되는 시도 이름이 있다면 확인 (예: "서울", "경기" 등)
        // assertThat(sidoName).isEqualTo("서울"); // 실제 데이터베이스 값에 맞게 수정
    }

    @Test
    @DisplayName("존재하지 않는 동기 코드에 대한 이름 조회 테스트")
    void selectMotiveNameForNonExistentCode() {
        // given
        int nonExistentCode = 9999; // 존재하지 않는 동기 코드

        // when
        String motiveName = recommendationDao.selectMotiveName(nonExistentCode);

        // then
        assertThat(motiveName).isNull(); // 존재하지 않는 코드에 대해서는 null 반환 예상
    }

    @Test
    @DisplayName("존재하지 않는.시도 코드에 대한 이름 조회 테스트")
    void selectSidoNameForNonExistentCode() {
        // given
        int nonExistentCode = 9999; // 존재하지 않는 시도 코드

        // when
        String sidoName = recommendationDao.selectSidoName(nonExistentCode);

        // then
        assertThat(sidoName).isNull(); // 존재하지 않는 코드에 대해서는 null 반환 예상
    }
}
package com.ssafy.live.domain.review.dao;

import com.ssafy.live.domain.review.dto.ReviewResponseDto;
import com.ssafy.live.domain.user.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ReviewDaoTest {

    @Autowired
    private ReviewDao reviewDao;

    @Test
    @DisplayName("리뷰 저장 테스트")
    void insertReview() {
        // given
        ReviewResponseDto newReview = createSampleReview();

        // when
        reviewDao.insertReview(newReview);

        // then
        ReviewResponseDto insertedReview = reviewDao.selectReviewById(newReview.getReviewId());
        assertThat(insertedReview).isNotNull();
        assertThat(insertedReview.getTitle()).isEqualTo("테스트 리뷰 제목");
        assertThat(insertedReview.getContent()).isEqualTo("테스트 리뷰 내용");
        assertThat(insertedReview.getUserId()).isEqualTo(1);
        assertThat(insertedReview.getSpotId()).isEqualTo(56644);
        // 새 필드 검증
        assertThat(insertedReview.getMotiveCode()).isEqualTo(2);
        assertThat(insertedReview.getComNum()).isEqualTo(3);
    }

    @Test
    @DisplayName("리뷰 수정 테스트")
    void updateReview() {
        // given
        ReviewResponseDto newReview = createSampleReview();
        reviewDao.insertReview(newReview);

        // when
        newReview.setTitle("수정된 리뷰 제목");
        newReview.setContent("수정된 리뷰 내용");
        newReview.setRating(5.0f);
        // 새 필드 수정
        newReview.setMotiveCode(4);
        newReview.setComNum(5);
        newReview.setModifiedAt(LocalDateTime.now());
        reviewDao.updateReview(newReview);

        // then
        ReviewResponseDto updatedReview = reviewDao.selectReviewById(newReview.getReviewId());
        assertThat(updatedReview).isNotNull();
        assertThat(updatedReview.getTitle()).isEqualTo("수정된 리뷰 제목");
        assertThat(updatedReview.getContent()).isEqualTo("수정된 리뷰 내용");
        assertThat(updatedReview.getRating()).isEqualTo(5.0f);
        // 새 필드 검증
        assertThat(updatedReview.getMotiveCode()).isEqualTo(4);
        assertThat(updatedReview.getComNum()).isEqualTo(5);
    }

    @Test
    @DisplayName("리뷰 삭제 테스트")
    void deleteReview() {
        // given
        ReviewResponseDto newReview = createSampleReview();
        reviewDao.insertReview(newReview);
        int reviewId = newReview.getReviewId();

        // when
        reviewDao.deleteReview(reviewId);

        // then
        ReviewResponseDto deletedReview = reviewDao.selectReviewById(reviewId);
        assertThat(deletedReview).isNull();
    }

    @Test
    @DisplayName("리뷰 ID로 조회 테스트")
    void selectReviewById() {
        // given
        ReviewResponseDto newReview = createSampleReview();
        reviewDao.insertReview(newReview);
        int reviewId = newReview.getReviewId();

        // when
        ReviewResponseDto foundReview = reviewDao.selectReviewById(reviewId);

        // then
        assertThat(foundReview).isNotNull();
        assertThat(foundReview.getReviewId()).isEqualTo(reviewId);
        assertThat(foundReview.getTitle()).isEqualTo("테스트 리뷰 제목");
        assertThat(foundReview.getContent()).isEqualTo("테스트 리뷰 내용");
        // 새 필드 검증
        assertThat(foundReview.getMotiveCode()).isEqualTo(2);
        assertThat(foundReview.getComNum()).isEqualTo(3);
    }

    @Test
    @DisplayName("존재하지 않는 리뷰 ID로 조회 테스트")
    void selectReviewByIdNonExistent() {
        // given
        int nonExistentId = 99999; // 존재하지 않는 ID

        // when
        ReviewResponseDto review = reviewDao.selectReviewById(nonExistentId);

        // then
        assertThat(review).isNull();
    }

    @Test
    @DisplayName("사용자 ID로 리뷰 목록 조회 테스트")
    void selectReviewsByUserId() {
        // given
        ReviewResponseDto newReview = createSampleReview();
        reviewDao.insertReview(newReview);
        int userId = newReview.getUserId();

        // when
        List<ReviewResponseDto> userReviews = reviewDao.selectReviewsByUserId(userId);

        // then
        assertThat(userReviews).isNotNull();
        assertThat(userReviews).isNotEmpty();
        assertThat(userReviews.stream().anyMatch(r -> r.getReviewId() == newReview.getReviewId())).isTrue();
        // 새 필드 검증 (최소 하나의 리뷰에서)
        assertThat(userReviews.stream()
            .filter(r -> r.getReviewId() == newReview.getReviewId())
            .findFirst()
            .map(ReviewResponseDto::getMotiveCode)
            .orElse(null)).isEqualTo(2);
        assertThat(userReviews.stream()
            .filter(r -> r.getReviewId() == newReview.getReviewId())
            .findFirst()
            .map(ReviewResponseDto::getComNum)
            .orElse(null)).isEqualTo(3);
    }

    @Test
    @DisplayName("관광지 ID로 리뷰 목록 조회 테스트")
    void selectReviewsBySpotId() {
        // given
        ReviewResponseDto newReview = createSampleReview();
        reviewDao.insertReview(newReview);
        int spotId = newReview.getSpotId();

        // when
        List<ReviewResponseDto> spotReviews = reviewDao.selectReviewsBySpotId(spotId);

        // then
        assertThat(spotReviews).isNotNull();
        assertThat(spotReviews).isNotEmpty();
        assertThat(spotReviews.stream().anyMatch(r -> r.getReviewId() == newReview.getReviewId())).isTrue();
        // 새 필드 검증 (최소 하나의 리뷰에서)
        assertThat(spotReviews.stream()
            .filter(r -> r.getReviewId() == newReview.getReviewId())
            .findFirst()
            .map(ReviewResponseDto::getMotiveCode)
            .orElse(null)).isEqualTo(2);
        assertThat(spotReviews.stream()
            .filter(r -> r.getReviewId() == newReview.getReviewId())
            .findFirst()
            .map(ReviewResponseDto::getComNum)
            .orElse(null)).isEqualTo(3);
    }

    @Test
    @DisplayName("리뷰 작성자 ID 조회 테스트")
    void selectReviewWriterId() {
        // given
        ReviewResponseDto newReview = createSampleReview();
        reviewDao.insertReview(newReview);
        int reviewId = newReview.getReviewId();

        // when
        int writerId = reviewDao.selectReviewWriterId(reviewId);

        // then
        assertThat(writerId).isEqualTo(newReview.getUserId());
    }

    /**
     * 테스트용 리뷰 샘플 생성
     */
    private ReviewResponseDto createSampleReview() {
        ReviewResponseDto review = new ReviewResponseDto();
        review.setUserId(1);
        review.setUsername("testuser");
        review.setSpotId(56644); // SpotDaoTest에서 사용된 ID 활용
        review.setSpotName("테스트 관광지");
        review.setTitle("테스트 리뷰 제목");
        review.setContent("테스트 리뷰 내용");
        review.setRating(4.5f);
        review.setReviewLike(0);
        review.setCreatedAt(LocalDateTime.now());
        
        // 새 필드 설정
        review.setMotiveCode(2); // 동기 코드 (예: 2 = 휴양)
        review.setComNum(3);     // 동반자 수 (예: 3명)
        
        // UserDto 설정 (필요한 경우)
        // UserDto userDto = new UserDto();
        // userDto.setId(1);
        // userDto.setUsername("testuser");
        // review.setUser(userDto);
        
        return review;
    }
}
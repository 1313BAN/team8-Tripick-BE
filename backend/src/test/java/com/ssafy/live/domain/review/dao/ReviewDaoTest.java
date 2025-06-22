package com.ssafy.live.domain.review.dao;

import com.ssafy.live.common.AbstractIntegrationTest;
import com.ssafy.live.domain.review.dto.ReviewResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@ActiveProfiles("test")
public class ReviewDaoTest extends AbstractIntegrationTest {

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
        assertThat(insertedReview.getUserId()).isEqualTo(1000012);
        assertThat(insertedReview.getNo()).isEqualTo(1);
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
        newReview.setRating(5.0);
        newReview.setModifiedAt(LocalDateTime.now());
        reviewDao.updateReview(newReview);

        // then
        ReviewResponseDto updatedReview = reviewDao.selectReviewById(newReview.getReviewId());
        assertThat(updatedReview).isNotNull();
        assertThat(updatedReview.getTitle()).isEqualTo("수정된 리뷰 제목");
        assertThat(updatedReview.getContent()).isEqualTo("수정된 리뷰 내용");
        assertThat(updatedReview.getRating()).isEqualTo(5.0);
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
        assertThat(userReviews.stream()
                .anyMatch(r -> r.getReviewId() == newReview.getReviewId()))
                .isTrue();
    }

    @Test
    @DisplayName("관광지 ID로 리뷰 목록 조회 테스트")
    void selectReviewsBySpotNo() {
        // given
        ReviewResponseDto newReview = createSampleReview();
        reviewDao.insertReview(newReview);
        int spotNo = newReview.getNo();

        // when
        List<ReviewResponseDto> spotReviews = reviewDao.selectReviewsBySpotNo(spotNo);

        // then
        assertThat(spotReviews).isNotNull();
        assertThat(spotReviews).isNotEmpty();
        assertThat(spotReviews.stream()
                .anyMatch(r -> r.getReviewId() == newReview.getReviewId()))
                .isTrue();
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
        review.setUserId(1000012);
        review.setUsername("testuser");
        review.setNo(1); // SpotDaoTest에서 사용된 ID 활용
        review.setSpotName("테스트 관광지");
        review.setTitle("테스트 리뷰 제목");
        review.setContent("테스트 리뷰 내용");
        review.setRating(4.0); // Double 타입
        review.setReviewLike(0);
        review.setCreatedAt(LocalDateTime.now());

        return review;
    }
}
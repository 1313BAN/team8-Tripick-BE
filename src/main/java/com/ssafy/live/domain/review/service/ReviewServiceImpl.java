package com.ssafy.live.domain.review.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.live.domain.review.dao.ReviewDao;
import com.ssafy.live.domain.review.dto.ReviewRequestDto;
import com.ssafy.live.domain.review.dto.ReviewResponseDto;
import com.ssafy.live.domain.spot.dao.SpotDao;
import com.ssafy.live.domain.spot.dto.SpotDto;
import com.ssafy.live.domain.spot.service.SpotService; 
import com.ssafy.live.domain.user.dao.UserDao;
import com.ssafy.live.domain.user.dto.UserDto;
import com.ssafy.live.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;
    private final UserDao userDao;
    private final SpotDao spotDao;
    
    // 리뷰 작성
    @Override
    @Transactional
    public ReviewResponseDto createReview(int userId, ReviewRequestDto reviewRequestDto) {
        // 사용자 존재 확인 및 정보 가져오기
        UserDto user = userDao.findById(userId);
        if (user == null) {
            throw new NoSuchElementException("존재하지 않는 사용자입니다.");
        }

        // 관광지 존재 확인
        SpotDto spot = spotDao.selectSpotByNo(reviewRequestDto.getSpotId());
        if (spot == null) {
            throw new NoSuchElementException("존재하지 않는 관광지입니다.");
        }

       // 사용자가 이미 해당 관광지에 리뷰를 작성했는지 확인
       List<ReviewResponseDto> userReviews = reviewDao.selectReviewsByUserId(userId);
       boolean alreadyReviewed = userReviews.stream()
           .anyMatch(r -> r.getSpotId() == reviewRequestDto.getSpotId());
       if (alreadyReviewed) {
           throw new IllegalStateException("이미 해당 관광지에 리뷰를 작성하셨습니다.");
       }
    
       // rating 값 검증 (1-5 범위)
       if (reviewRequestDto.getRating() < 1 || reviewRequestDto.getRating() > 5) {
           throw new IllegalArgumentException("평점은 1에서 5 사이의 값이어야 합니다.");
       }
        
        // 리뷰 정보 설정
        ReviewResponseDto reviewDto = new ReviewResponseDto();
        reviewDto.setUserId(userId);
        reviewDto.setUsername(user.getName()); // 사용자 이름 설정
        reviewDto.setUser(user); // 사용자 전체 정보 설정
        reviewDto.setSpotId(reviewRequestDto.getSpotId());
        reviewDto.setSpotName(spot.getTitle()); // 관광지 이름 설정
        reviewDto.setRating(reviewRequestDto.getRating());
        reviewDto.setTitle(reviewRequestDto.getTitle());
        reviewDto.setContent(reviewRequestDto.getContent());
        reviewDto.setReviewLike(0); // 초기 좋아요 수는 0

        // 리뷰 저장
        reviewDao.insertReview(reviewDto);

        // 저장된 리뷰 조회 (auto-increment된 ID 포함)
        return reviewDao.selectReviewById(reviewDto.getReviewId());
    }
    
    @Override
    @Transactional
    public ReviewResponseDto updateReview(int userId, int reviewId, ReviewRequestDto reviewRequestDto) {
        // 리뷰 존재 여부 확인
        ReviewResponseDto existingReview = reviewDao.selectReviewById(reviewId);
        if (existingReview == null) {
            throw new NoSuchElementException("존재하지 않는 리뷰입니다.");
        }
        
        // 리뷰 작성자와 현재 사용자가 일치하는지 확인
        int writerId = reviewDao.selectReviewWriterId(reviewId);
        if (writerId != userId) {
            throw new SecurityException("리뷰 수정 권한이 없습니다.");
        }
    
       // 입력 데이터 유효성 검사
       if (reviewRequestDto.getRating() < 1 || reviewRequestDto.getRating() > 5) {
           throw new IllegalArgumentException("평점은 1에서 5 사이의 값이어야 합니다.");
       }
       
       if (reviewRequestDto.getTitle() == null || reviewRequestDto.getTitle().trim().isEmpty()) {
           throw new IllegalArgumentException("리뷰 제목은 필수입니다.");
       }
       
       if (reviewRequestDto.getContent() == null || reviewRequestDto.getContent().trim().isEmpty()) {
           throw new IllegalArgumentException("리뷰 내용은 필수입니다.");
    }
       
        
        
        // 리뷰 정보 업데이트
        existingReview.setRating(reviewRequestDto.getRating());
        existingReview.setTitle(reviewRequestDto.getTitle());
        existingReview.setContent(reviewRequestDto.getContent());
        
        // 리뷰 수정
        reviewDao.updateReview(existingReview);
        
        // 수정된 리뷰 조회
        return reviewDao.selectReviewById(reviewId);
    }
    
    @Override
    @Transactional
    public void deleteReview(int userId, int reviewId) {
        // 리뷰 존재 여부 확인
        if (reviewDao.selectReviewById(reviewId) == null) {
            throw new NoSuchElementException("존재하지 않는 리뷰입니다.");
        }
        
        // 리뷰 작성자와 현재 사용자가 일치하는지 확인
        int writerId = reviewDao.selectReviewWriterId(reviewId);
        if (writerId != userId) {
            throw new SecurityException("리뷰 삭제 권한이 없습니다.");
        }
        
        
        // 리뷰 삭제
        reviewDao.deleteReview(reviewId);
    }

    @Override
    public ReviewResponseDto getReviewById(int reviewId) {
        ReviewResponseDto review = reviewDao.selectReviewById(reviewId);
        if (review == null) {
            throw new NoSuchElementException("존재하지 않는 리뷰입니다.");
        }
        return review;
    }
    
    @Override
    public List<ReviewResponseDto> getReviewsByUserId(int userId) {
        // 사용자 존재 확인
    	UserDto user = userDao.findById(userId);
        if (user == null) {
            throw new NoSuchElementException("존재하지 않는 사용자입니다.");
        }
        
        // 사용자의 리뷰 목록 조회
        return reviewDao.selectReviewsByUserId(userId);
    }
    
    @Override
    public List<ReviewResponseDto> getReviewsBySpotId(int spotId) {
        
        // 관광지 존재 확인
        SpotDto spot = spotDao.selectSpotByNo(spotId);
        if (spot == null) {
            throw new NoSuchElementException("존재하지 않는 관광지입니다.");
        }
        
        // 관광지의 리뷰 목록 조회
        return reviewDao.selectReviewsBySpotId(spotId);
    }
    
}
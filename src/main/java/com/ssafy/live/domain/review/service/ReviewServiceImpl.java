package com.ssafy.live.domain.review.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;
    private final UserDao userDao; // 필요한 경우 사용자 정보를 조회하기 위한 DAO
    private final SpotDao spotDao; // 필요한 경우 관광지 정보를 조회하기 위한 DAO

    @Override
    public ReviewResponseDto createReview(ReviewRequestDto requestDto, Long userId) {
        // 요청 DTO를 바로 MyBatis에 전달
        ReviewResponseDto responseDto = new ReviewResponseDto();
        responseDto.setUserId(userId);
        responseDto.setSpotId(requestDto.getSpotId());
        responseDto.setRating(requestDto.getRating());
        responseDto.setContent(requestDto.getContent());
        responseDto.setTitle(requestDto.getTitle());
        responseDto.setReviewLike(0); // 초기 좋아요 수는 0
        
        // DAO를 통해 DB에 저장
        reviewDao.insertReview(responseDto); // 이 메소드는 ID를 설정함
        
        // 추가 정보 설정 (사용자 이름, 관광지 이름 등)
        enrichResponseDto(responseDto);
        
        return responseDto;
    }

    @Override
    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto requestDto, Long userId) {
        // 리뷰 소유자 확인
        if (!reviewDao.isReviewOwner(reviewId, userId)) {
            throw new AccessDeniedException("해당 리뷰를 수정할 권한이 없습니다.");
        }
        
        // 기존 리뷰 조회
        ReviewResponseDto existingReview = reviewDao.selectReviewById(reviewId);
        if (existingReview == null) {
            throw new RuntimeException("해당 리뷰를 찾을 수 없습니다: " + reviewId);
        }
        
        // 리뷰 정보 업데이트
        existingReview.setRating(requestDto.getRating());
        existingReview.setContent(requestDto.getContent());
        existingReview.setTitle(requestDto.getTitle());
        // spotId는 변경하지 않음 (리뷰가 작성된 관광지는 변경 불가)
        
        // DAO를 통해 DB 업데이트
        reviewDao.updateReview(existingReview);
        
        // 추가 정보 설정
        enrichResponseDto(existingReview);
        
        return existingReview;
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) {
        // 리뷰 소유자 확인
        if (!reviewDao.isReviewOwner(reviewId, userId)) {
            throw new AccessDeniedException("해당 리뷰를 삭제할 권한이 없습니다.");
        }
        
        // DAO를 통해 리뷰 삭제
        reviewDao.deleteReview(reviewId);
    }

    @Override
    public List<ReviewResponseDto> getAllReviewsBySpotId(Long spotId) {
        // DAO를 통해 관광지별 리뷰 조회
        List<ReviewResponseDto> reviews = reviewDao.selectReviewsBySpotId(spotId);
        
        // 각 리뷰에 추가 정보 설정
        reviews.forEach(this::enrichResponseDto);
        
        return reviews;
    }

    @Override
    public List<ReviewResponseDto> getAllReviewsByUserId(Long userId) {
        // DAO를 통해 사용자별 리뷰 조회
        List<ReviewResponseDto> reviews = reviewDao.selectReviewsByUserId(userId);
        
        // 각 리뷰에 추가 정보 설정
        reviews.forEach(this::enrichResponseDto);
        
        return reviews;
    }

    @Override
    public ReviewResponseDto getReviewById(Long reviewId) {
        // DAO를 통해 리뷰 조회
        ReviewResponseDto review = reviewDao.selectReviewById(reviewId);
        if (review == null) {
            throw new RuntimeException("해당 리뷰를 찾을 수 없습니다: " + reviewId);
        }
        
        // 추가 정보 설정
        enrichResponseDto(review);
        
        return review;
    }
    
    // ReviewResponseDto에 추가 정보(사용자 이름, 관광지 이름)를 설정하는 메서드
    private void enrichResponseDto(ReviewResponseDto responseDto) {
        // 사용자 이름 설정
        try {
            UserDto user = userDao.selectUserById(responseDto.getUserId());
            if (user != null) {
                responseDto.setUsername(user.getUsername());
            }
        } catch (Exception e) {
            // 사용자 정보 조회 실패시 무시
        }
        
        // 관광지 이름 설정
        try {
            SpotDto spot = spotDao.selectSpotById(responseDto.getSpotId());
            if (spot != null) {
                responseDto.setSpotName(spot.getName());
            }
        } catch (Exception e) {
            // 관광지 정보 조회 실패시 무시
        }
    }
}
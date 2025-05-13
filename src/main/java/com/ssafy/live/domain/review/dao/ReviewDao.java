package com.ssafy.live.domain.review.dao;

import java.util.List;

import com.ssafy.live.domain.review.dto.ReviewResponseDto;

public interface ReviewDao {
	
    // 리뷰 생성
    void insertReview(ReviewResponseDto reviewDto);
    
    // 리뷰 조회
    ReviewResponseDto selectReviewById(Long reviewId);
    
    // 리뷰 수정
    void updateReview(ReviewResponseDto reviewDto);
    
    // 리뷰 삭제
    void deleteReview(Long reviewId);
    
    // 관광지별 리뷰 조회
    List<ReviewResponseDto> selectReviewsBySpotId(Long spotId);
    
    // 사용자별 리뷰 조회
    List<ReviewResponseDto> selectReviewsByUserId(Long userId);
}
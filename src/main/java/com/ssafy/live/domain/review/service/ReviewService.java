package com.ssafy.live.domain.review.service;

import java.util.List;

import com.ssafy.live.domain.review.dto.ReviewRequestDto;
import com.ssafy.live.domain.review.dto.ReviewResponseDto;

public interface ReviewService {
    // 리뷰 작성
    ReviewResponseDto createReview(int userId, ReviewRequestDto reviewRequestDto);
    
    // 리뷰 수정
    ReviewResponseDto updateReview(int userId, int reviewId, ReviewRequestDto reviewRequestDto);
    
    // 리뷰 삭제
    void deleteReview(int userId, int reviewId);
    
    // 리뷰 상세 보기
    ReviewResponseDto getReviewById(int reviewId);
    
    // 사용자별 리뷰 보기
    List<ReviewResponseDto> getReviewsByUserId(int userId);
    
    // 관광지별 리뷰 보기
    List<ReviewResponseDto> getReviewsBySpotId(int spotId);
}
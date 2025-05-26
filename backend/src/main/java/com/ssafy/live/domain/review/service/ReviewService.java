package com.ssafy.live.domain.review.service;

import java.util.List;

import com.ssafy.live.domain.review.dto.ReviewRequestDto;
import com.ssafy.live.domain.review.dto.ReviewResponseDto;

public interface ReviewService {
    ReviewResponseDto createReview(int userId, ReviewRequestDto reviewRequestDto);
    ReviewResponseDto updateReview(int userId, int reviewId, ReviewRequestDto reviewRequestDto);
    void deleteReview(int userId, int reviewId);
    ReviewResponseDto getReviewById(int reviewId);
    List<ReviewResponseDto> getReviewsByUserId(int userId);
    List<ReviewResponseDto> getReviewsBySpotNo(int spotNo); // spotId → spotNo로 변경
}
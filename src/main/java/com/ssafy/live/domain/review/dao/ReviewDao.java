package com.ssafy.live.domain.review.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.live.domain.review.dto.ReviewResponseDto;

@Mapper
public interface ReviewDao {
    
    // 리뷰 저장
    void insertReview(ReviewResponseDto reviewDto);
    
    // 리뷰 수정
    void updateReview(ReviewResponseDto reviewDto);
    
    // 리뷰 삭제
    void deleteReview(int reviewId);
    
    // 리뷰 ID로 조회
    ReviewResponseDto selectReviewById(int reviewId);
    
    // 사용자 ID로 리뷰 목록 조회
    List<ReviewResponseDto> selectReviewsByUserId(int userId);
    
    // 관광지 ID로 리뷰 목록 조회
    List<ReviewResponseDto> selectReviewsBySpotId(int spotId);
    
    // 리뷰 작성자 ID 조회
    int selectReviewWriterId(int reviewId);
}
package com.ssafy.live.domain.review.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.live.domain.review.dto.ReviewRequestDto;
import com.ssafy.live.domain.review.dto.ReviewResponseDto;
import com.ssafy.live.domain.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
  
    private final ReviewService reviewService;
    
    /**
     * 리뷰 작성 API
     * 
     * @param userDetails 인증된 사용자 정보
     * @param reviewRequestDto 리뷰 작성 정보
     * @return 생성된 리뷰 정보
     */
    @PostMapping
    public ResponseEntity<?> createReview(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ReviewRequestDto reviewRequestDto) {
        // 재윤이형한테 id도 넣어달라고 하기
        int userId = Integer.parseInt(userDetails.);
        ReviewResponseDto responseDto = reviewService.createReview(userId, reviewRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    
    /**
     * 리뷰 수정 API
     * 
     * @param userDetails 인증된 사용자 정보
     * @param reviewId 수정할 리뷰 ID
     * @param reviewRequestDto 수정할 리뷰 정보
     * @return 수정된 리뷰 정보
     */
    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int reviewId,
            @RequestBody ReviewRequestDto reviewRequestDto) {
        
        int userId = Integer.parseInt(userDetails.getUsername());
        ReviewResponseDto responseDto = reviewService.updateReview(userId, reviewId, reviewRequestDto);
        return ResponseEntity.ok(responseDto);
    }
    
    /**
     * 리뷰 삭제 API
     * 
     * @param userDetails 인증된 사용자 정보
     * @param reviewId 삭제할 리뷰 ID
     * @return 상태 코드
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int reviewId) {
        
        int userId = Integer.parseInt(userDetails.getUsername());
        reviewService.deleteReview(userId, reviewId);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * 리뷰 상세 조회 API
     * 
     * @param reviewId 조회할 리뷰 ID
     * @return 리뷰 상세 정보
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<?> getReviewDetail(@PathVariable int reviewId) {
        ReviewResponseDto responseDto = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(responseDto);
    }
    
    /**
     * 사용자별 리뷰 목록 조회 API
     * 
     * @param userId 조회할 사용자 ID
     * @return 사용자의 리뷰 목록
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<?>> getReviewsByUser(@PathVariable int userId) {
        List<ReviewResponseDto> reviewList = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(reviewList);
    }
    
    /**
     * 관광지별 리뷰 목록 조회 API
     * 
     * @param spotId 조회할 관광지 ID
     * @return 관광지의 리뷰 목록
     */
    @GetMapping("/spots/{spotId}")
    public ResponseEntity<List<?>> getReviewsBySpot(@PathVariable int spotId) {
        List<ReviewResponseDto> reviewList = reviewService.getReviewsBySpotId(spotId);
        return ResponseEntity.ok(reviewList);
    }
    
}
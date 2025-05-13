package com.ssafy.live.domain.review.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.live.domain.user.dto.UserDto;

import lombok.Data;
import lombok.Getter; 
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class ReviewResponseDto {
    // 리뷰 기본 정보
    private int reviewId;
    private int spotId;
    private String spotName;
    private Float rating;
    private int reviewLike;
    private String content;
    private String title;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;
    
    // 사용자 정보 (기존 필드는 남겨두고 중복 객체도 추가)
    private int userId;
    private String username; // 호환성을 위해 유지
    
    // 사용자 상세 정보
    private UserDto user; // 사용자 전체 정보를 포함하는 객체
}
package com.ssafy.live.domain.review.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.live.domain.user.dto.UserDto;

import lombok.Getter; 
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
    
    // 사용자 상세 정보
    private UserDto user; // 사용자 전체 정보를 포함하는 객체
}
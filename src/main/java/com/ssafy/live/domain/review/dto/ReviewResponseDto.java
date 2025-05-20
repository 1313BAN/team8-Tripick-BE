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
    private int no; // spotId를 no로 변경 (테이블 컬럼명과 일치)
    private String spotName; // 조인 쿼리에서 가져올 필드
    private Double rating; // Float에서 Double로 변경 (테이블 타입과 일치)
    private int reviewLike;
    private String content;
    private String title;
    private Integer initData; // 테이블에 있는 필드 추가

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    // 새로 추가된 필드
    private Integer motiveCode;
    private Integer comNum;

    // 사용자 정보 
    private int userId;
    private String username; // 호환성을 위해 유지

    // 사용자 상세 정보
    private UserDto user; // 사용자 전체 정보를 포함하는 객체
}

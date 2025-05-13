package com.ssafy.live.domain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequestDto {
    private int spotId; // 관광지 ID (no)
    private Float rating; // 별점
    private String content; // 내용
    private String title; // 제목
}

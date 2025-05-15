package com.ssafy.live.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter; 
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ReviewRequestDto {
	@NotNull(message = "관광지 ID는 필수 항목입니다")
    private int spotId; // 관광지 ID (no)
	
	@NotNull(message = "별점은 필수 항목입니다")
	@Min(value = 1, message = "별점은 1점 이상이어야 합니다")
	@Max(value = 5, message = "별점은 5점 이하여야 합니다")
    private Float rating; // 별점
	
	@NotBlank(message = "리뷰 내용은 필수 항목입니다")
	@Size(min = 10, max = 1000, message = "리뷰 내용은 10자 이상 1000자 이하여야 합니다")
    private String content; // 내용
	
	@NotBlank(message = "리뷰 제목은 필수 항목입니다")
	@Size(min = 5, max = 100, message = "리뷰 제목은 5자 이상 100자 이하여야 합니다")
    private String title; // 제목
}

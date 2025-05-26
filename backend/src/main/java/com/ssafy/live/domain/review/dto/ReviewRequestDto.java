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
    private int no; // spotId를 no로 변경 (테이블 컬럼명과 일치)

    @NotNull(message = "별점은 필수 항목입니다")
    @Min(value = 1, message = "별점은 1점 이상이어야 합니다")
    @Max(value = 5, message = "별점은 5점 이하여야 합니다")
    private Double rating; // Float에서 Double로 변경 (테이블 타입과 일치)

    @NotBlank(message = "리뷰 내용은 필수 항목입니다")
    @Size(min = 10, max = 1000, message = "리뷰 내용은 10자 이상 1000자 이하여야 합니다")
    private String content;

    @NotBlank(message = "리뷰 제목은 필수 항목입니다")
    @Size(min = 5, max = 50, message = "리뷰 제목은 5자 이상 50자 이하여야 합니다") // 50자로 제한 (테이블 컬럼 크기)
    private String title;

    // 새로 추가된 필드
    private Integer motiveCode;

    @Min(value = 1, message = "동반자 수는 1명 이상이어야 합니다")
    private Integer comNum;
}
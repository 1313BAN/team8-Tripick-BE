package com.ssafy.live.domain.recommdation.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryRecommendationDTO {
    private String categoryName;       // 카테고리 이름 (예: "당신의 나이와 성별에 맞는 추천")
    private String categoryDescription; // 카테고리 설명 (예: "20-30대 남성이 높은 평점을 준 장소")
    private List<SpotRecommendationDTO> spots; // 추천 여행지 목록
}
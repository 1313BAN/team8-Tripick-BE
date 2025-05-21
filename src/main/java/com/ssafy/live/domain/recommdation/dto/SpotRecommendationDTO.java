package com.ssafy.live.domain.recommdation.dto;

import lombok.Data;

@Data
public class SpotRecommendationDTO {
    private Integer no;
    private String title;
    private String firstImage1;
    private String addr;
    private String overview;
    private Double latitude;
    private Double longitude;
    private Double avgRating;
    private Integer contentTypeId;
    private String contentTypeName; // JOIN해서 가져온 컨텐츠 타입명
    private Integer reviewCount;    // 해당 장소의 리뷰 수
}
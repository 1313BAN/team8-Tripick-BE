package com.ssafy.live.domain.recommdation.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RecommendationRequestDTO {
    private String gender;
    private Integer minAge;
    private Integer maxAge;
    private Integer motiveCode;
    private Integer contentTypeId;
    private Integer areaCode;
    private Integer siGunGuCode;
    private Integer limit = 10; // 기본값 10개
}
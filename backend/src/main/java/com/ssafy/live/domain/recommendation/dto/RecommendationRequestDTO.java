package com.ssafy.live.domain.recommendation.dto;

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

    /**
     * 캐시 키 생성을 위한 toString() 재정의
     * null 값을 안전하게 처리하여 일관된 키 생성
     */
    @Override
    public String toString() {
        return String.format("%s_%s_%s_%s_%s_%s_%s_%s", 
            gender != null ? gender : "null",
            minAge != null ? minAge : "null", 
            maxAge != null ? maxAge : "null",
            motiveCode != null ? motiveCode : "null",
            contentTypeId != null ? contentTypeId : "null", 
            areaCode != null ? areaCode : "null",
            siGunGuCode != null ? siGunGuCode : "null",
            limit != null ? limit : 10
        );
    }
    
    // 해시 키 생성
    public String generateMeaningfulCacheKey() {
        StringBuilder key = new StringBuilder("recommendations");
        
        if (gender != null) key.append(":gender:").append(gender);
        if (minAge != null && maxAge != null) key.append(":age:").append(minAge).append("-").append(maxAge);
        if (motiveCode != null) key.append(":motive:").append(motiveCode);
        if (contentTypeId != null) key.append(":content:").append(contentTypeId);
        if (areaCode != null) key.append(":area:").append(areaCode);
        key.append(":limit:").append(limit);
        
        return key.toString();
    }
    
}
package com.ssafy.live.domain.spot.dto;



import java.math.BigDecimal;

import lombok.Data;


@Data
public class SpotDto {
    private int no;
    private int contentId;
    private String title;
    private int contentTypeId;
    private int areaCode;
    private int siGunGuCode;
    private String firstImage1;
    private String firstImage2;
    private int mapLevel;
    private BigDecimal latitude;  
    private BigDecimal longitude; 
    private String tel;
    private String addr1;
    private String addr2;
    private String homepage;
    private String overview;
}

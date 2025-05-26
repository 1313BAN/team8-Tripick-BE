package com.ssafy.live.domain.spot.dto;



import java.math.BigDecimal;

import lombok.Data;


@Data
public class SpotDto {
    private int no;
    private String title;
    private int contentTypeId;
    private int areaCode;
    private int siGunGuCode;
    private String firstImage1;
    private String firstImage2;
    private double latitude;
    private double longitude;
    private String addr;
    private String homepage;
    private String overview;
}
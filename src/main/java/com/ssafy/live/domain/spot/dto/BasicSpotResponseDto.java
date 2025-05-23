package com.ssafy.live.domain.spot.dto;



import java.math.BigDecimal; 

import lombok.Data;

//기본 장소 응답 DTO
public class BasicSpotResponseDto {
 private int no;
 private String title;
 private int contentTypeId;
 private double latitude;
 private double longitude;
 private double averageRating;
 private int reviewCount;
 
 public BasicSpotResponseDto() {}
 
 public BasicSpotResponseDto(int no, String title, int contentTypeId, 
                            double latitude, double longitude, 
                            double averageRating, int reviewCount) {
     this.no = no;
     this.title = title;
     this.contentTypeId = contentTypeId;
     this.latitude = latitude;
     this.longitude = longitude;
     this.averageRating = averageRating;
     this.reviewCount = reviewCount;
 }
 
 // Getters and Setters
 public int getNo() {
     return no;
 }
 
 public void setNo(int no) {
     this.no = no;
 }
 
 public String getTitle() {
     return title;
 }
 
 public void setTitle(String title) {
     this.title = title;
 }
 
 public int getContentTypeId() {
     return contentTypeId;
 }
 
 public void setContentTypeId(int contentTypeId) {
     this.contentTypeId = contentTypeId;
 }
 
 public double getLatitude() {
     return latitude;
 }
 
 public void setLatitude(double latitude) {
     this.latitude = latitude;
 }
 
 public double getLongitude() {
     return longitude;
 }
 
 public void setLongitude(double longitude) {
     this.longitude = longitude;
 }
 
 public double getAverageRating() {
     return averageRating;
 }
 
 public void setAverageRating(double averageRating) {
     this.averageRating = averageRating;
 }
 
 public int getReviewCount() {
     return reviewCount;
 }
 
 public void setReviewCount(int reviewCount) {
     this.reviewCount = reviewCount;
 }
}
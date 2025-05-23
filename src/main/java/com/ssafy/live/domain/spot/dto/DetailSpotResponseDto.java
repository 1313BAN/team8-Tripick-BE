package com.ssafy.live.domain.spot.dto;

import lombok.Data;

//상세 장소 응답 DTO
public class DetailSpotResponseDto extends BasicSpotResponseDto {
 private AgeRatingDto ageRatings;
 private String mostPopularAccompanyType;
 private String mostPopularMotive;
 
 public DetailSpotResponseDto() {}
 
 public DetailSpotResponseDto(int no, String title, int contentTypeId, 
                             double latitude, double longitude, 
                             double averageRating, int reviewCount,
                             AgeRatingDto ageRatings, 
                             String mostPopularAccompanyType, 
                             String mostPopularMotive) {
     super(no, title, contentTypeId, latitude, longitude, averageRating, reviewCount);
     this.ageRatings = ageRatings;
     this.mostPopularAccompanyType = mostPopularAccompanyType;
     this.mostPopularMotive = mostPopularMotive;
 }
 
 // Getters and Setters
 public AgeRatingDto getAgeRatings() {
     return ageRatings;
 }
 
 public void setAgeRatings(AgeRatingDto ageRatings) {
     this.ageRatings = ageRatings;
 }
 
 public String getMostPopularAccompanyType() {
     return mostPopularAccompanyType;
 }
 
 public void setMostPopularAccompanyType(String mostPopularAccompanyType) {
     this.mostPopularAccompanyType = mostPopularAccompanyType;
 }
 
 public String getMostPopularMotive() {
     return mostPopularMotive;
 }
 
 public void setMostPopularMotive(String mostPopularMotive) {
     this.mostPopularMotive = mostPopularMotive;
 }
}
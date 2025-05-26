package com.ssafy.live.domain.spot.dto;

import lombok.Data;
//연령대별 평점 DTO
public class AgeRatingDto {
 private double twenties;  // 20대
 private double thirties;  // 30대
 private double forties;   // 40대
 private double fifties;   // 50대
 private double sixties;   // 60대 이상
 
 public AgeRatingDto() {}
 
 public AgeRatingDto(double twenties, double thirties, double forties, 
                    double fifties, double sixties) {
     this.twenties = twenties;
     this.thirties = thirties;
     this.forties = forties;
     this.fifties = fifties;
     this.sixties = sixties;
 }
 
 // Getters and Setters
 public double getTwenties() {
     return twenties;
 }
 
 public void setTwenties(double twenties) {
     this.twenties = twenties;
 }
 
 public double getThirties() {
     return thirties;
 }
 
 public void setThirties(double thirties) {
     this.thirties = thirties;
 }
 
 public double getForties() {
     return forties;
 }
 
 public void setForties(double forties) {
     this.forties = forties;
 }
 
 public double getFifties() {
     return fifties;
 }
 
 public void setFifties(double fifties) {
     this.fifties = fifties;
 }
 
 public double getSixties() {
     return sixties;
 }
 
 public void setSixties(double sixties) {
     this.sixties = sixties;
 }
}
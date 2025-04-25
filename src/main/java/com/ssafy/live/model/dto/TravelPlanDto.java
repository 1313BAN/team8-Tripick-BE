package com.ssafy.live.model.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class TravelPlanDto {
    private int planId;
    private int userId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // 여행계획에 포함된 관광지 매핑 목록
    private List<TravelPlanAttractionDto> attractions;

    public int getPlanId() {
        return planId;
    }
    public void setPlanId(int planId) {
        this.planId = planId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    public List<TravelPlanAttractionDto> getAttractions() {
        return attractions;
    }
    public void setAttractions(List<TravelPlanAttractionDto> attractions) {
        this.attractions = attractions;
    }
}

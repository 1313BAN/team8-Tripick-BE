package com.ssafy.live.model.dto;

public class TravelPlanAttractionDto {
    private int planAttractionId;
    private int planId;
    private int attractionId;
    private int sequence; // 방문 순서

    public int getPlanAttractionId() {
        return planAttractionId;
    }
    public void setPlanAttractionId(int planAttractionId) {
        this.planAttractionId = planAttractionId;
    }
    public int getPlanId() {
        return planId;
    }
    public void setPlanId(int planId) {
        this.planId = planId;
    }
    public int getAttractionId() {
        return attractionId;
    }
    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }
    public int getSequence() {
        return sequence;
    }
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}

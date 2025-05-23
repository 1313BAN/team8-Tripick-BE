package com.ssafy.live.domain.recommendation.dto;

public record AICategoryRecommendResponse(
        int no,
        String title,
        String addr,
        String type,
        String accompanySummary,
        String motiveSummary,
        String reason) {
}
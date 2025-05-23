package com.ssafy.live.domain.spot.dto;

public record SpotVectorDto(
        int no,
        String title,
        String addr,
        String typeName,
        String accompanySummary,
        String motiveSummary) {
}
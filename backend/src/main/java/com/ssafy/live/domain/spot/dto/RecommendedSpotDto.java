package com.ssafy.live.domain.spot.dto;

public record RecommendedSpotDto(
                int no, // 관광지 번호
                String title, // 이름
                String addr, // 주소
                String type, // 유형 (예: 산책로, 테마파크 등)
                String accompanySummary, // 자주 방문하는 동행 요약 (예: 연인, 가족)
                String motiveSummary, // 주요 여행 동기 요약 (예: 힐링, 교육적 목적)
                String reason // GPT가 생성한 추천 이유
) {
}
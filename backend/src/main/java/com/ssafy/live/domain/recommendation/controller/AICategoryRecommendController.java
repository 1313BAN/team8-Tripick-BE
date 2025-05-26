package com.ssafy.live.domain.recommendation.controller;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.ssafy.live.domain.recommendation.dto.AICategoryRecommendRequest;
import com.ssafy.live.domain.recommendation.dto.AICategoryRecommendResponse;
import com.ssafy.live.domain.recommendation.service.AICategoryRecommendService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai-recommend")
@RequiredArgsConstructor
@Profile("!test")
public class AICategoryRecommendController {

    private final AICategoryRecommendService recommendService;

    @PostMapping("/category")
    public List<AICategoryRecommendResponse> recommend(@RequestBody AICategoryRecommendRequest requestDto) {
        List<AICategoryRecommendResponse> recommendList = recommendService.recommendSpots(requestDto);
        System.out.println("🧠 응답 내용: " + recommendList);
        return recommendList;
    }
}
package com.ssafy.live.domain.recommendation.service;

import com.ssafy.live.domain.recommendation.dto.*;
import java.util.List;

public interface AICategoryRecommendService {
    List<AICategoryRecommendResponse> recommendSpots(AICategoryRecommendRequest request);
}
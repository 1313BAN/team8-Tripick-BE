package com.ssafy.live.domain.recommendation.service;

import com.ssafy.live.domain.recommendation.dto.*;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@ConditionalOnMissingBean(VectorStore.class)
@Profile("!test")
public class AICategoryRecommendServiceImpl implements AICategoryRecommendService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Override
    public List<AICategoryRecommendResponse> recommendSpots(AICategoryRecommendRequest request) {
        // 카테고리들을 문자열로 병합
        String categoryPrompt = String.join(", ", request.categories());

        // GPT를 사용해 유저 선호 카테고리 임베딩
        List<Document> similarSpots = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(categoryPrompt)
                        .topK(3)
                        .build());

        // 각 관광지에 대해 GPT에게 추천 이유 요청
        return similarSpots.parallelStream().map(doc -> {
            var meta = doc.getMetadata();

            String noStr = String.valueOf(meta.getOrDefault("no", "-1")); // 기본값: -1
            int no = Integer.parseInt(noStr);

            String title = String.valueOf(meta.getOrDefault("title", "제목 없음"));
            String addr = String.valueOf(meta.getOrDefault("addr", "주소 없음"));
            String type = String.valueOf(meta.getOrDefault("type", "유형 없음"));
            String accompany = String.valueOf(meta.getOrDefault("accompany", "정보 없음"));
            String motive = String.valueOf(meta.getOrDefault("motive", "정보 없음"));

            String gptPrompt = """
                    사용자가 선호하는 카테고리: %s

                    추천 관광지:
                    - 이름: %s
                    - 주소: %s
                    - 유형: %s
                    - 자주 방문하는 동행 유형: %s
                    - 주요 여행 동기: %s

                    위 정보를 기반으로 해당 관광지를 추천하는 이유를 20자 이내로 한글로 간단히 작성해줘.
                    """.formatted(categoryPrompt, title, addr, type, accompany, motive);

            System.out.println("Thread: " + Thread.currentThread().getName() + " | 요청 시작");
            String reason = chatClient.prompt().user(gptPrompt).call().content();
            System.out.println("Thread: " + Thread.currentThread().getName() + " | 응답 완료");

            return new AICategoryRecommendResponse(
                    no, title, addr, type, accompany, motive, reason);
        }).toList();
    }
}
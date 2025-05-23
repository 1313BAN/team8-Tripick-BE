package com.ssafy.live.domain.spot.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.ssafy.live.domain.spot.dao.SpotDao;
import com.ssafy.live.domain.spot.dto.SpotVectorDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpotVectorService {

    private final SpotDao spotDao;
    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public void storeAllToVector() {
        List<SpotVectorDto> spots = spotDao.selectAllForVector();

        List<Document> docs = spots.stream().map(s -> {
            String prompt = """
                    다음 정보를 바탕으로 관광지를 매력적으로 소개해줘:

                    - 이름: %s
                    - 유형: %s
                    - 주소: %s
                    - 자주 방문하는 동행 유형: %s
                    - 주요 여행 동기: %s
                    """.formatted(s.title(), s.typeName(), s.addr(), s.accompanySummary(), s.motiveSummary());

            String content = chatClient.prompt().user(prompt).call().content();

            return new Document(content, Map.of(
                    "no", s.no(),
                    "title", s.title(),
                    "addr", s.addr(),
                    "type", s.typeName(),
                    "accompany", s.accompanySummary(),
                    "motive", s.motiveSummary()));
        }).toList();

        vectorStore.add(docs);
    }

}

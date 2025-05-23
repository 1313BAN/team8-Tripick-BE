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

    // public void storeAllToVector() {
    // List<SpotVectorDto> spots = spotDao.selectAllForVector();

    // List<Document> docs = spots.stream().map(s -> {
    // System.out.println("✅ 처리 중: " + s.no() + " - " + s.title());

    // String prompt = """
    // 다음 정보를 바탕으로 관광지를 매력적으로 소개해줘:

    // - 이름: %s
    // - 유형: %s
    // - 주소: %s
    // - 자주 방문하는 동행 유형: %s
    // - 주요 여행 동기: %s
    // """.formatted(
    // s.title(), s.typeName(), s.addr(),
    // s.accompanySummary() != null ? s.accompanySummary() : "정보 없음",
    // s.motiveSummary() != null ? s.motiveSummary() : "정보 없음");

    // String content = chatClient.prompt().user(prompt).call().content();

    // return new Document(content, Map.of(
    // "no", s.no(),
    // "title", s.title(),
    // "addr", s.addr(),
    // "type", s.typeName(),
    // "accompany", s.accompanySummary() != null ? s.accompanySummary() : "없음",
    // "motive", s.motiveSummary() != null ? s.motiveSummary() : "없음"));
    // }).toList();
    // System.out.println("✅ Redis 저장 시작 (" + docs.size() + "건)");
    // vectorStore.add(docs);
    // System.out.println("✅ 저장 완료");
    // }

    public void storeAllToVector() {
        List<SpotVectorDto> spots = spotDao.selectAllForVector();

        for (SpotVectorDto s : spots) {
            System.out.println("📝 관광지 정보:");
            System.out.println("  번호: " + s.no());
            System.out.println("  이름: " + s.title());
            System.out.println("  유형: " + s.typeName());
            System.out.println("  주소: " + s.addr());
            System.out.println("  동행 요약: " + s.accompanySummary());
            System.out.println("  여행 동기 요약: " + s.motiveSummary());
            System.out.println("-------------------------------------------------");
        }
        List<Document> docs = spots.stream().map(s -> {
            // System.out.println("✅ 벡터화 대상: " + s.no() + " - " + s.title());

            // 기존 정보들을 하나의 문자열로 합쳐 임베딩에 사용할 content 구성
            String content = """
                    이름: %s
                    유형: %s
                    주소: %s
                    동행: %s
                    여행 동기: %s
                    """.formatted(
                    s.title(),
                    s.typeName(),
                    s.addr(),
                    s.accompanySummary() != null ? s.accompanySummary() : "정보 없음",
                    s.motiveSummary() != null ? s.motiveSummary() : "정보 없음");

            return new Document(content, Map.of(
                    "no", s.no(),
                    "title", s.title(),
                    "addr", s.addr(),
                    "type", s.typeName(),
                    "accompany", s.accompanySummary() != null ? s.accompanySummary() : "없음",
                    "motive", s.motiveSummary() != null ? s.motiveSummary() : "없음"));
        }).toList();

        System.out.println("📦 생성된 Document 목록:");
        for (Document doc : docs) {
            System.out.println("🧾 Content:\n" + doc.getText());
            System.out.println("🔖 Metadata:");
            doc.getMetadata().forEach((k, v) -> System.out.println("  " + k + ": " + v));
            System.out.println("-------------------------------------------------");
        }

        // System.out.println("✅ Redis 저장 시작 (" + docs.size() + "건)");
        // vectorStore.add(docs);
        // System.out.println("✅ 저장 완료");
    }

}

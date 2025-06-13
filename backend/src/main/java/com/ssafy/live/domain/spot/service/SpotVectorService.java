package com.ssafy.live.domain.spot.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.ssafy.live.domain.spot.dao.SpotDao;
import com.ssafy.live.domain.spot.dto.SpotVectorDto;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Profile("!test")
public class SpotVectorService {

    private final SpotDao spotDao;
    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public void storeAllToVector() {
        List<SpotVectorDto> spots = spotDao.selectAllForVector();
        int THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 2;
        int BATCH_SIZE = 50;

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<List<Document>>> futures = new ArrayList<>();

        // Batch 처리
        for (int i = 0; i < spots.size(); i += BATCH_SIZE) {
            int fromIndex = i;
            int toIndex = Math.min(i + BATCH_SIZE, spots.size());

            List<SpotVectorDto> batch = spots.subList(fromIndex, toIndex);

            Callable<List<Document>> task = () -> {
                List<Document> batchDocs = new ArrayList<>();
                for (SpotVectorDto s : batch) {
                    System.out.println("✅ 처리 중: " + s.no() + " - " + s.title());

                    String prompt = """
                            다음 정보를 바탕으로 관광지를 매력적으로 소개해줘:

                            - 이름: %s
                            - 유형: %s
                            - 주소: %s
                            - 자주 방문하는 동행 유형: %s
                            - 주요 여행 동기: %s
                            """.formatted(
                            s.title(), s.typeName(), s.addr(),
                            s.accompanySummary() != null ? s.accompanySummary() : "정보 없음",
                            s.motiveSummary() != null ? s.motiveSummary() : "정보 없음");

                    String content = chatClient.prompt().user(prompt).call().content();

                    Document doc = new Document(content, Map.of(
                            "no", s.no(),
                            "title", s.title(),
                            "addr", s.addr(),
                            "type", s.typeName(),
                            "accompany", s.accompanySummary() != null ? s.accompanySummary() : "없음",
                            "motive", s.motiveSummary() != null ? s.motiveSummary() : "없음"));
                    batchDocs.add(doc);
                }
                return batchDocs;
            };

            futures.add(executor.submit(task));
        }

        // 결과 취합 및 저장
        List<Document> allDocs = futures.stream().flatMap(f -> {
            try {
                return f.get().stream();
            } catch (Exception e) {
                e.printStackTrace();
                return Stream.empty();
            }
        }).collect(Collectors.toList());

        System.out.println("✅ Redis 저장 시작 (" + allDocs.size() + "건)");
        vectorStore.add(allDocs);
        System.out.println("✅ 저장 완료");

        executor.shutdown();
    }

    // public void storeAllToVector() {
    // List<SpotVectorDto> spots = spotDao.selectAllForVector();
    // List<Document> docs = spots.stream().map(s -> {
    // // System.out.println("✅ 벡터화 대상: " + s.no() + " - " + s.title());

    // // 기존 정보들을 하나의 문자열로 합쳐 임베딩에 사용할 content 구성
    // String content = """
    // 이름: %s
    // 유형: %s
    // 주소: %s
    // 동행: %s
    // 여행 동기: %s
    // """.formatted(
    // s.title(),
    // s.typeName(),
    // s.addr(),
    // s.accompanySummary() != null ? s.accompanySummary() : "정보 없음",
    // s.motiveSummary() != null ? s.motiveSummary() : "정보 없음");

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

}

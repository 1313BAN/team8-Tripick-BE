package com.ssafy.live.domain.spot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
public class SpotVectorInspectorTest {

    @Autowired
    private VectorStore vectorStore;

    private static final Logger log = LoggerFactory.getLogger(SpotVectorInspectorTest.class);

    @Test
    void countNullFieldsDocuments() {
        List<Document> docs = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query("dummy")
                        .topK(1000)
                        .build());

        int nullCount = 0;
        for (Document doc : docs) {
            Map<String, Object> meta = doc.getMetadata();

            boolean allNull = meta.get("no") == null &&
                    meta.get("title") == null &&
                    meta.get("addr") == null &&
                    meta.get("type") == null &&
                    meta.get("accompany") == null &&
                    meta.get("motive") == null;

            if (allNull) {
                nullCount++;
            }
        }

        log.info("🧾 Documents with all null metadata fields: {}", nullCount);
    }

}

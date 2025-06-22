package com.ssafy.live.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.TestConfiguration;

import static org.mockito.Mockito.*;

@TestConfiguration
@ActiveProfiles("test")
public class TestVectorStoreConfig {

    @Bean
    @Primary
    public VectorStore vectorStore() {
        // ✅ 모든 메서드를 기본적으로 no-op 처리하는 mock 객체 반환
        return mock(VectorStore.class);
    }
}

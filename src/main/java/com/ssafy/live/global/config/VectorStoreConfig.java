package com.ssafy.live.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.redis.RedisVectorStore.MetadataField;
import redis.clients.jedis.JedisPooled;

@Configuration
public class VectorStoreConfig {

    @Bean
    public VectorStore vectorStore(
            EmbeddingModel embeddingModel,
            @Value("${spring.ai.vectorstore.redis.index}") String index,
            @Value("${spring.ai.vectorstore.redis.prefix}") String prefix) {

        return RedisVectorStore.builder(new JedisPooled("localhost", 6379), embeddingModel)
                .indexName(index)
                .prefix(prefix)
                .metadataFields(
                        MetadataField.tag("type"),
                        MetadataField.text("title"),
                        MetadataField.text("addr"))
                .initializeSchema(false) // 이미 index가 생성되어 있다면 false
                .build();
    }
}

package com.ssafy.live.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.redis.RedisVectorStore.MetadataField;
import redis.clients.jedis.JedisPooled;

@Configuration
@ConditionalOnMissingBean(VectorStore.class)
@Profile("!test")
public class VectorStoreConfig {

        @Bean
        public VectorStore vectorStore(
                        EmbeddingModel embeddingModel,
                        @Value("${spring.ai.vectorstore.redis.index}") String index,
                        @Value("${spring.ai.vectorstore.redis.prefix}") String prefix,
                        @Value("${spring.data.redis.host}") String redisHost,
                        @Value("${spring.data.redis.port}") int redisPort) {
                return RedisVectorStore.builder(new JedisPooled(redisHost, redisPort), embeddingModel)
                                .indexName(index)
                                .prefix(prefix)
                                .metadataFields(
                                                MetadataField.tag("type"),
                                                MetadataField.text("title"),
                                                MetadataField.text("addr"),
                                                MetadataField.tag("accompany"),
                                                MetadataField.tag("motive"))
                                .initializeSchema(true)
                                .build();
        }
}

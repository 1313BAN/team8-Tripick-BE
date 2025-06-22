package com.ssafy.live.common;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import com.zaxxer.hikari.HikariDataSource;

import org.testcontainers.containers.GenericContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;

import com.ssafy.live.config.TestVectorStoreConfig;
import org.springframework.context.annotation.Import;

@Import(TestVectorStoreConfig.class)
@SpringBootTest(properties = {
        "spring.datasource.hikari.maximum-pool-size=5",
        "spring.datasource.hikari.minimum-idle=2",
        "spring.datasource.hikari.connection-timeout=30000",
        "spring.datasource.hikari.idle-timeout=10000",
        "spring.datasource.hikari.max-lifetime=30000"
})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void resetConnectionPool() {
        if (dataSource instanceof HikariDataSource hikariDataSource) {
            hikariDataSource.getHikariPoolMXBean().softEvictConnections();
        }
    }

    @Autowired
    ApplicationContext context;

    @AfterAll
    public void tearDown() {
        try {
            DataSource ds = context.getBean(DataSource.class);
            if (ds instanceof HikariDataSource hikariDataSource) {
                hikariDataSource.close(); // 커넥션 풀 닫기
            }
        } catch (Exception ignored) {}
    }

    static final MySQLContainer<?> mysqlContainer;
    static final GenericContainer<?> redisContainer;

    static {
        mysqlContainer = new MySQLContainer<>("mysql:8.0")
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpass");
        mysqlContainer.start();

        redisContainer = new GenericContainer<>("redis:7.2.4")
                .withExposedPorts(6379);
        redisContainer.start();
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        // MySQL 설정
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);

        // Redis 설정
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));

        // 기타 JPA 설정
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");

        registry.add("spring.datasource.hikari.max-lifetime", () -> "30000");
        registry.add("spring.datasource.hikari.connection-timeout", () -> "30000");
    }
}

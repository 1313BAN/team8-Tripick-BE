# === 1단계: 빌드 스테이지 ===
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# backend 폴더 안의 모든 파일 복사
COPY . .

# mvn clean package 실행 (테스트 생략)
RUN mvn clean package -DskipTests

# === 2단계: 실행 스테이지 ===
FROM openjdk:17-jdk-slim
WORKDIR /app

# 빌드한 jar 복사
COPY --from=builder /app/target/*.jar app.jar

# Spring Boot 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Tripick

> 쉽고 편리한 맞춤형 여행지 추천 서비스

부모님들이 좋은 관광지를 탐색하는데 어려움을 겪는 문제를 해결하기 위한 직관적이고 간편한 여행지 추천 플랫폼입니다.

## 주요 기능

**스마트 추천 시스템**
- AI 기반 개인화된 관광지 추천
- 관광지 관련 정보를 임베딩 모델을 통해 Redis에 저장
- 사용자 선호도 기반 맞춤형 추천

**성능 최적화**
- Redis 캐싱을 통한 고성능 데이터 처리
- cache-aside 패턴으로 빠른 데이터 접근
- 콜드 스타트 문제 해결

**보안 시스템**
- JWT 토큰 블랙리스트 기반 안전한 로그아웃
- 사용자 인증 및 권한 관리

## 아키텍처

![그림1](https://github.com/user-attachments/assets/f1cae687-cb52-4949-a932-a55e030aff1a)



## 기술 스택

**Backend**
- Spring Boot, MySQL, Redis, JWT

**Frontend**
- Vue.js

**Infrastructure**
- AWS (S3, CloudFront), NGINX, Docker, GitHub Actions


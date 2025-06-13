FROM ollama/ollama:latest

# curl 설치
RUN apt-get update && apt-get install -y curl

# entrypoint.sh 복사 및 실행 권한 부여
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
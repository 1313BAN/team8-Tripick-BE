#!/bin/bash

set -e  # 에러 발생 시 스크립트 즉시 종료

# Ollama 서버 백그라운드 실행
ollama serve &

# 서버가 열릴 때까지 대기 (11434 포트 기준)
echo "🕐 Ollama 서버가 준비될 때까지 대기 중..."
until curl -s http://localhost:11434 > /dev/null; do
  sleep 1
done
echo "✅ Ollama 서버가 준비되었습니다."

# 모델 목록
MODELS=("mistral" "nomic-embed-text")

# 필요한 모델만 다운로드 (이미 있으면 skip)
for model in "${MODELS[@]}"; do
  if ollama list | grep -q "$model"; then
    echo "📦 모델 '$model' 이미 존재함. 스킵합니다."
  else
    echo "⬇️ 모델 '$model' 다운로드 중..."
    ollama pull "$model"
  fi
done

# 컨테이너 유지 (서버는 백그라운드에서 실행 중)
echo "🚀 Ollama 컨테이너가 실행 중입니다."
tail -f /dev/null

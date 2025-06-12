#!/bin/bash
set -e

# 載入 .env 環境變數
export $(grep -v '^#' .env | xargs)

echo "==== Gradle Build ===="
./gradlew clean build -x test

echo "==== Docker Compose Up ===="
docker compose --env-file .env up -d

echo "==== 完成！服務已啟動 ===="

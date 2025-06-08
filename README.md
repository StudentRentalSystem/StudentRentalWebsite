# Redis 使用說明（StudentRentalWebsite 專案）

本專案使用 Docker Compose 快速啟動一個 Redis 伺服器，用於開發時的資料快取或訊息佇列等用途。

---

## 📦 環境需求

- 已安裝 [Docker](https://www.docker.com/)
- 已安裝 [Docker Compose](https://docs.docker.com/compose/)

---

## 🚀 快速啟動

1. 開啟終端機或 PowerShell，移動到專案根目錄：

   ```bash
   cd "Path\To\StudentRentalWebsite"

2. 執行 Docker Compose 啟動 Redis：
   ```bash
   docker-compose up -d

3. Redis 伺服器將會在背景啟動，預設使用 localhost:6379 連接。


## 📁 Volume 持久化說明
Redis 的資料會儲存在名為 redis-data 的 volume 中，重啟容器後資料仍會保留。

## 🛑 停止 Redis
若要停止 Redis 伺服器：
```bash
docker-compose down
```

這會停止並移除 Redis 容器，但資料不會刪除。

若要連同資料一起刪除，請加上 -v：
```bash
docker-compose down -v
```


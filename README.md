# Redis 使用說明（StudentRentalWebsite 專案）

本專案使用 Docker Compose 快速啟動一個 Redis 伺服器，用於開發時的資料快取或訊息佇列等用途。

---

## 📦 環境需求

- 已安裝 [Docker](https://www.docker.com/)
- 已安裝 [Docker Compose](https://docs.docker.com/compose/)

---
使用說明
1. 建立 .env 檔案
   在專案根目錄建立 .env，填入你的環境變數，例如：
```
MONGO_ROOT_USERNAME=admin
MONGO_ROOT_PASSWORD=admin123
MONGO_DB=student_rental

GOOGLE_CLIENT_ID=your-client-id.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=your-client-secret

CLIENT_TOKEN=ghp_xxxxxxxxxxxxxxxxxxxxxx
```
注意： 請勿將 .env 推上版本庫。

2. 使用一條指令快速啟動服務
   本專案附有自動化腳本 build-and-up.sh，會自動：

- 載入 .env

- 執行 Gradle build

- 啟動 Docker Compose

執行以下指令：
```bash
chmod +x build-and-up.sh
./build-and-up.sh
```
3. 查看服務狀態
```bash
   docker-compose ps

```
4. 停止服務
   ```bash
   docker-compose down
   ```
### 常見問題
1. Gradle 抓不到環境變數？
確保你有執行 build-and-up.sh，或手動先執行 export $(grep -v '^#' .env | xargs)。
2. MongoDB 連線失敗？
請確認 .env 中的 MongoDB 帳密與 SPRING_DATA_MONGO_URI 的設定一致。
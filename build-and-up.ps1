# 停止出錯繼續
$ErrorActionPreference = "Stop"

Write-Host "`n==== 載入 .env 環境變數 ===="

# 讀取 .env 並設定 PowerShell session 的環境變數
Get-Content .env | Where-Object { $_ -and ($_ -notmatch '^\s*#') } | ForEach-Object {
    $parts = $_ -split '=', 2
    if ($parts.Count -eq 2) {
        $name = $parts[0].Trim()
        $value = $parts[1].Trim()
        [System.Environment]::SetEnvironmentVariable($name, $value)
    }
}

Write-Host "`n==== Gradle Build ===="
./gradlew clean build -x test

# 檢查 JAR 是否產出
if (-Not (Test-Path "./build/libs/*.jar")) {
    Write-Error "❌ 找不到 JAR 檔案，請確認 Gradle 是否成功執行"
    exit 1
}

Write-Host "`n==== Docker Compose Up ===="
docker compose --env-file .env up -d

Write-Host "`n✅ 完成！服務已啟動 🎉"

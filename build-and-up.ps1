# 停止出錯繼續
$ErrorActionPreference = "Stop"

Write-Host "==== 載入 .env 環境變數 ===="

# 讀取 .env 並設定環境變數
Get-Content .env | Where-Object { $_ -and ($_ -notmatch '^\s*#') } | ForEach-Object {
    $parts = $_ -split '=', 2
    if ($parts.Count -eq 2) {
        $name = $parts[0].Trim()
        $value = $parts[1].Trim()
        # 設定環境變數
        [System.Environment]::SetEnvironmentVariable($name, $value)
    }
}

Write-Host "==== Gradle Build ===="
./gradlew clean build -x test

Write-Host "==== Docker Compose Up ===="
docker-compose --env-file .env up -d

Write-Host "==== 完成！服務已啟動 ===="

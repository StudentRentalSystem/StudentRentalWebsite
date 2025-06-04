function toggleCollect(button) {
    const postId = button.getAttribute("data-post-id");
    let isCollected = button.getAttribute("data-collected") === "true";
    const method = isCollected ? "DELETE" : "POST";



    // Debug log
    console.log("🪪 postId =", postId);
    console.log("📦 method =", method);

    // 錯誤防呆
    if (!postId) {
        alert("⚠️ 收藏失敗，使用者或貼文資訊缺失");
        return;
    }

    fetch(`/collect/${postId}`, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include'
    })
        .then(res => {
            if (!res.ok) {
                throw new Error("HTTP 錯誤：" + res.status);
            }
            return res.json();
        })
        .then(data => {
            console.log("✅ 後端回傳：", data);

            if (data.status === 'success') {
                // 切換收藏狀態
                isCollected = !isCollected;
                button.setAttribute("data-collected", isCollected);

                const icon = button.querySelector("i");
                const text = button.querySelector("span");

                if (isCollected) {
                    button.classList.remove("btn-outline-secondary");
                    button.classList.add("btn-danger");
                    icon.classList.remove("fa-heart-o");
                    icon.classList.add("fa-heart");
                    text.textContent = "取消收藏";
                } else {
                    button.classList.remove("btn-danger");
                    button.classList.add("btn-outline-secondary");
                    icon.classList.remove("fa-heart");
                    icon.classList.add("fa-heart-o");
                    text.textContent = "收藏";
                }
            } else {
                alert("操作失敗：" + (data.message || "未知原因"));
            }
        })
        .catch(err => {
            console.error("❌ 收藏請求錯誤：", err);
            alert("⚠️ 系統錯誤，請稍後再試");
        });
}

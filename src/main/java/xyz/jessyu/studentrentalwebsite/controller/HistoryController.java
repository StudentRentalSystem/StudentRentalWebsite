package xyz.jessyu.studentrentalwebsite.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import xyz.jessyu.studentrentalwebsite.service.CustomOAuth2Service;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/historylist")
public class HistoryController {

    private final CustomOAuth2Service customOAuth2Service;

    @Autowired
    public HistoryController(CustomOAuth2Service customOAuth2Service) {
        this.customOAuth2Service = customOAuth2Service;
    }

    // ✅ 紀錄搜尋關鍵字
    @PostMapping
    public ResponseEntity<?> recordSearch(@RequestParam String keyword,
                                          @AuthenticationPrincipal OAuth2User oAuth2User) {

        System.out.println("📩 收到紀錄請求 keyword: " + keyword);

        String email = getOAuth2Email(oAuth2User);
        if (email == null) return unauthorized();

        boolean success = customOAuth2Service.addHistory(email, keyword);
        if (success) {
            return ResponseEntity.ok(Map.of("status", "success", "message", "已紀錄搜尋"));
        } else {
            return ResponseEntity.status(400).body(Map.of("status", "fail", "message", "紀錄失敗"));
        }
    }

    // 📜 取得歷史紀錄
    @GetMapping
    public ResponseEntity<?> getHistory(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String email = getOAuth2Email(oAuth2User);
        if (email == null) return unauthorized();

        LinkedHashMap<String, String> history = customOAuth2Service.getSortedHistory(email);
        return ResponseEntity.ok(Map.of("status", "success", "history", history));
    }

    // 🧠 抽出 email
    private String getOAuth2Email(OAuth2User oAuth2User) {
        if (oAuth2User == null) return null;
        return oAuth2User.getAttribute("email");
    }

    // 🛡️ 未登入處理
    private ResponseEntity<?> unauthorized() {
        return ResponseEntity.status(401).body(Map.of("status", "fail", "message", "尚未登入"));
    }
}

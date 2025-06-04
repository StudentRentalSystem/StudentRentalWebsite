package xyz.jessyu.studentrentalwebsite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import xyz.jessyu.studentrentalwebsite.service.CustomOAuth2Service;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collect")
public class CollectionController {

    private final CustomOAuth2Service customOAuth2Service;

    public CollectionController(CustomOAuth2Service customOAuth2Service) {
        this.customOAuth2Service = customOAuth2Service;
    }

    // ✅ 收藏貼文
    @PostMapping("/{postId}")
    public ResponseEntity<?> collect(@PathVariable String postId,
                                     @AuthenticationPrincipal OAuth2User oAuth2User) {

        System.out.println("collecting");

        String email = getOAuth2Email(oAuth2User);
        if (email == null) return unauthorized();

        boolean success = customOAuth2Service.addCollection(email, postId);
        if (success) {
            return ResponseEntity.ok(Map.of("status", "success", "message", "收藏成功"));
        } else {
            return ResponseEntity.status(400).body(Map.of("status", "fail", "message", "重複收藏"));
        }
    }

    // ❌ 取消收藏
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> uncollect(@PathVariable String postId,
                                       @AuthenticationPrincipal OAuth2User oAuth2User) {

        System.out.println("uncollecting");

        String email = getOAuth2Email(oAuth2User);
        if (email == null) return unauthorized();

        boolean success = customOAuth2Service.deleteCollection(email, postId);
        if (success) {
            return ResponseEntity.ok(Map.of("status", "success", "message", "取消收藏成功"));
        } else {
            return ResponseEntity.status(400).body(Map.of("status", "fail", "message", "尚未收藏"));
        }
    }

    // 📦 查詢收藏
    @GetMapping
    public ResponseEntity<?> getCollection(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String email = getOAuth2Email(oAuth2User);
        if (email == null) return unauthorized();

        List<String> collections = customOAuth2Service.getCollections(email);
        return ResponseEntity.ok(Map.of("status", "success", "collections", collections));
    }

    // 🔐 OAuth2 使用者 email 抽出
    private String getOAuth2Email(OAuth2User oAuth2User) {
        if (oAuth2User == null) return null;
        return oAuth2User.getAttribute("email");
    }

    // 🔒 未登入處理
    private ResponseEntity<?> unauthorized() {
        return ResponseEntity.status(401).body(Map.of("status", "fail", "message", "尚未登入"));
    }
}

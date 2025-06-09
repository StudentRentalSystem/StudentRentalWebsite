package xyz.jessyu.studentrentalwebsite.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoQueryException;
import io.github.querygenerator.MiniRagApp;
import io.github.studentrentalsystem.LLMConfig;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import xyz.jessyu.studentrentalwebsite.model.RentalInfo;
import xyz.jessyu.studentrentalwebsite.repository.RentalInfoRepository;
import xyz.jessyu.studentrentalwebsite.service.MiniRagAppService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class LoadPostController {
    @Autowired
    private MiniRagAppService miniRagAppService;

    @Autowired
    private RentalInfoRepository rentalRepo;
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * The queryJSON is cacheable when the query is valid.
     * */
    @GetMapping("/searchposts")
    @Cacheable(value = "queryCache", key = "#keyword != null ? #keyword : 'empty'")
    public List<Map<String, Object>> loadPosts(@RequestParam(value = "keyword", required = false) String keyword) {
        MiniRagApp miniRagApp = miniRagAppService.getMiniRagApp();
        System.out.println("🔍 searchposts 被呼叫");
        System.out.println("搜尋關鍵字: " + keyword);

        if (keyword == null || keyword.isEmpty()) {
            System.out.println("關鍵字為空，返回 null");
            return null;
        }

        // Get the query from model and then transform it from JSON to Document structure
        Document queryDoc = null;
        try {
            JSONObject queryJson = miniRagApp.getMongoDBSearchCmdJSON(keyword);
            queryJson = miniRagApp.getFixedMongoQueryCmd(queryJson);
            System.out.println("生成的 query JSON : " + queryJson);
            queryDoc = Document.parse(queryJson.toString());
        } catch (JSONException | NullPointerException e) {
            System.out.println("JSON 語法錯誤");
            return null;
        }

        if (queryDoc.isEmpty()) {
            System.out.println("查詢條件為空");
            return null;
        }


        Query query = new BasicQuery(queryDoc);
        List<RentalInfo> data = null;

        try {
            data = mongoTemplate.find(query, RentalInfo.class);
        } catch (MongoQueryException e) {
            System.out.println("查詢語法錯誤");
            return null;
        }

        if (data == null || data.isEmpty()) {
            System.out.println("查詢結果為空");
            return null;
        }

        // 將查詢結果轉換為 Map，並處理 _id 欄位
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> result = new ArrayList<>();
        for (RentalInfo rental : data) {
            Map<String, Object> map = mapper.convertValue(rental, new TypeReference<>() {});
            Map<String, String> idWrapper = Map.of("$oid", rental.getId());
            map.put("_id", idWrapper);
            result.add(map);
        }

        return result;
    }

}

package xyz.jessyu.studentrentalwebsite.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.querygenerator.MiniRagApp;
import io.github.studentrentalsystem.Utils;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import xyz.jessyu.studentrentalwebsite.model.RentalInfo;
import xyz.jessyu.studentrentalwebsite.repository.RentalInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class LoadPostController {
    private static final MiniRagApp miniRagApp = new MiniRagApp();
    @Autowired
    private RentalInfoRepository rentalRepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    private String getSearchJSON(String query) {
        try {
            JSONObject jsonObject = new JSONObject(query);
            String result = jsonObject.getString("response");
            return Utils.getStringJSON(result).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/searchposts")
    public List<Map<String, Object>> loadPosts(@RequestParam(value = "keyword", required = false) String keyword) {
        System.out.println("🔍 searchposts 被呼叫");
        System.out.println(keyword);
        String queryString = miniRagApp.getMongoDBSearchCmd(keyword);
        String obj = getSearchJSON(queryString);
        System.out.println("query :"  + obj);
        Document queryDoc = Document.parse(obj);
        Query query = new BasicQuery(queryDoc);
        List<RentalInfo> data =  mongoTemplate.find(query, RentalInfo.class);

        // 將每個 RentalInfo 轉回 Document 格式（保留 "_id": {"$oid": ...}）給前端用
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> result = new ArrayList<>();
        for (RentalInfo rental : data) {
            // 把 Java 物件轉成 Map，並加工 "_id"
            Map<String, Object> map = mapper.convertValue(rental, new TypeReference<>() {});
            Map<String, String> idWrapper = Map.of("$oid", rental.getId());
            map.put("_id", idWrapper);
            result.add(map);
        }

        return result;
    }
}

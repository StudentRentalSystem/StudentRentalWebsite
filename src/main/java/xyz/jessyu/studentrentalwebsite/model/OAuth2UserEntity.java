package xyz.jessyu.studentrentalwebsite.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection="users")
public class OAuth2UserEntity {
    @Id
    private String id;
    @Indexed(unique=true)
    private String email;
    private String name;
    private String picture;

    private List<String> collections = new ArrayList<String>();
    private Map<String, String> searchHistory = new HashMap<>();

    public OAuth2UserEntity(String email, String name, String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
    }
}

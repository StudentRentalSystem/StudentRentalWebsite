package xyz.jessyu.studentrentalwebsite.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.jessyu.studentrentalwebsite.model.OAuth2UserEntity;
import xyz.jessyu.studentrentalwebsite.repository.OAuth2UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Autowired
    private OAuth2UserRepository oAuth2UserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String picture = oauth2User.getAttribute("picture");
        OAuth2UserEntity oAuth2UserEntity = oAuth2UserRepository.findByEmail(email);
        if (oAuth2UserEntity == null) {
            OAuth2UserEntity user = new OAuth2UserEntity(email, name, picture);
            oAuth2UserRepository.save(user);
        }

        // add UserName to session
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attr != null) {
            HttpServletRequest request = attr.getRequest();
            HttpSession session = request.getSession(true);
            session.setAttribute("CurrentUser", name);
        }

        return oauth2User;
    }

    public boolean addCollection(String email, String postId) {
        OAuth2UserEntity user = oAuth2UserRepository.findByEmail(email);
        if (user == null) return false;

        List<String> collections = user.getCollections();
        if (!collections.contains(postId)) {
            collections.add(postId);
            user.setCollections(collections);
            oAuth2UserRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean deleteCollection(String email, String postId) {
        OAuth2UserEntity user = oAuth2UserRepository.findByEmail(email);
        if (user == null) return false;

        List<String> collections = user.getCollections();
        if (collections.remove(postId)) {
            user.setCollections(collections);
            oAuth2UserRepository.save(user);
            return true;
        }
        return false;
    }

    public List<String> getCollections(String email) {
        OAuth2UserEntity user = oAuth2UserRepository.findByEmail(email);
        if (user != null) {
            return user.getCollections();
        }
        return new ArrayList<>();
    }

    public boolean addHistory(String email, String searchHistory) {
        OAuth2UserEntity user = oAuth2UserRepository.findByEmail(email);
        if (user == null) return false;

        Map<String, String> histories = user.getSearchHistory();

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        histories.put(timestamp, searchHistory);
        user.setSearchHistory(histories);

        oAuth2UserRepository.save(user);
        return true;
    }

    public LinkedHashMap<String, String> getSortedHistory(String email) {
        OAuth2UserEntity user = oAuth2UserRepository.findByEmail(email);
        if (user == null || user.getSearchHistory() == null) {
            return new LinkedHashMap<>();
        }

        return user.getSearchHistory()
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey())) // 時間字串遞減排序
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

}

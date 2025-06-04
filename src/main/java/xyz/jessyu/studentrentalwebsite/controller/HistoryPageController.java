package xyz.jessyu.studentrentalwebsite.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.jessyu.studentrentalwebsite.service.CustomOAuth2Service;

import java.util.LinkedHashMap;

@Controller
public class HistoryPageController {

    @Autowired
    private CustomOAuth2Service customOAuth2Service;

    @RequestMapping("/history")
    public String HistoryPage(@AuthenticationPrincipal OAuth2User oAuth2User, Model model, HttpSession session) {

        System.out.println("HistoryPage");

        String email = null;
        String username = null;
        String picture = null;

        if (oAuth2User != null) {
            email = oAuth2User.getAttribute("email");
            username = oAuth2User.getAttribute("name");
            picture = oAuth2User.getAttribute("picture");
        } else if (session.getAttribute("CurrentUser") != null) {
            username = session.getAttribute("CurrentUser").toString();
        }

        if (username == null) return "redirect:/loginpage";
        if (email == null) return "redirect:/loginpage";

        model.addAttribute("UserName", username);
        model.addAttribute("UserPicture", picture);

        LinkedHashMap<String, String> history = customOAuth2Service.getSortedHistory(email);
        model.addAttribute("searchHistory", history);

        return "HistoryPage";
    }
}

package xyz.jessyu.studentrentalwebsite.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.jessyu.studentrentalwebsite.model.RentalInfo;
import xyz.jessyu.studentrentalwebsite.repository.RentalInfoRepository;
import xyz.jessyu.studentrentalwebsite.service.CustomOAuth2Service;


import java.util.List;

@Controller
public class CollectionPageController {

    @Autowired
    private CustomOAuth2Service customOAuth2Service;

    @Autowired
    private RentalInfoRepository rentalInfoRepository;

    @RequestMapping("/collection")
    public String CollectionPage(@AuthenticationPrincipal OAuth2User oAuth2User, Model model, HttpSession session) {

        System.out.println("CollectionPage");

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

        model.addAttribute("UserName", username);
        model.addAttribute("UserPicture", picture);

        if (email != null) {
            try {
                List<String> ids = customOAuth2Service.getCollections(email);
                System.out.println("🔍 收藏ID數量：" + ids.size());
                List<RentalInfo> posts = rentalInfoRepository.findAllById(ids);

                // 👉 重點來了！印出每筆資料內容：
                for (RentalInfo post : posts) {
                    System.out.println("📝 檢查 post: " + post.getId());
                    if (post.getLayout() == null) System.out.println("⚠️ layout 為 null");
                    if (post.getRentalRange() == null) System.out.println("⚠️ rentalRange 為 null");
                    if (post.getGenderLimit() == null) System.out.println("⚠️ genderLimit 為 null");
                    if (post.getContactInfos() == null || post.getContactInfos().isEmpty()) System.out.println("⚠️ contactInfos 為空");
                }

                model.addAttribute("posts", posts);
                model.addAttribute("collectedIds", ids);

            } catch (Exception e) {
                System.err.println("❌ DB 錯誤：" + e.getClass().getName() + " - " + e.getMessage());
                e.printStackTrace();
            }
        }



        return "CollectionPage";
    }
}

package xyz.jessyu.studentrentalwebsite.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainPageController {

    @GetMapping("/index")
    public String MainPage(@AuthenticationPrincipal OAuth2User oAuth2User, Model model, HttpSession session) {
        String username = null;
        String picture = null;


        if(oAuth2User != null) {
            username = oAuth2User.getAttribute("name");
            picture = oAuth2User.getAttribute("picture");

            System.out.println(username);
            model.addAttribute("UserName", username);
            model.addAttribute("UserPicture", picture);
        } else {
            // 2. 傳統 Session 登入判斷，只有當 Google OAuth2 未登入時使用
            Object userObj = session.getAttribute("CurrentUser");
            if (userObj != null) {
                username = userObj.toString();
            }

            // 3. 無登入資料，跳轉登入頁
            if (username == null) {
                return "redirect:/loginpage";
            }

            System.out.println("username:" + username);
            model.addAttribute("UserName", username);
            model.addAttribute("UserPicture", null);
        }

        /*
        JsonTransformer jsonTransformer = new JsonTransformer();
        List<RentalDataJsonStruct> Posts = jsonTransformer.JsonTransform("TestData/PostData.json");
        model.addAttribute("posts", Posts);
        */

        return "MainPage";
    }



    // All controllers use the same logout GetMapping
    // now can log out oAuth
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         Authentication  auth) {

        if(auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        // 清掉自己的 session（OAuth2User 也存在於 session 裡）
        request.getSession().invalidate();

        // 重新導向
        return "redirect:/loginpage";
    }

}

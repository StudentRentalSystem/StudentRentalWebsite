package xyz.jessyu.studentrentalwebsite.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.jessyu.studentrentalwebsite.service.UserService;

@Controller
public class LoginController {

    UserService userService = new UserService();

    @RequestMapping("/loginpage")
    public String LoginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String success,
                            Model model) {

        if("1".equals(error)) {
            model.addAttribute("LoginSignupError", "帳號或密碼錯誤");
        } else if("2".equals(error)) {
            model.addAttribute("LoginSignupError", "帳號名已存在");
        } else if("3".equals(error)) {
            model.addAttribute("LoginSignupError", "兩個密碼不匹配");
        }

        if ("true".equals(success)) {
            model.addAttribute("LoginSignupSuccess", "註冊成功！請登入");
        }

        System.out.println("LoginPage");
        return "LoginSignupPage";
    }


    /**
     *
     * @param LoginName
     * @param Password
     * @param session this one is recording who login now
     */
    @PostMapping("/login")
    public String LoginAction(@RequestParam String LoginName,
                              @RequestParam String Password,
                              HttpSession session,
                              Model model) {

        System.out.println("LoginAction");
        System.out.println(LoginName);
        System.out.println(Password);


        if(userService.validateUser(LoginName, Password)) {
            // session record user login now
            session.setAttribute("CurrentUser", LoginName);
            return "redirect:/index";
        } else {
            System.out.println("進入錯誤處理區塊");
            return "redirect:/loginpage?error=1";
        }
    }

    @PostMapping("/signup")
    public String signupAction(@RequestParam String signupName,
                               @RequestParam String signupPassword,
                               @RequestParam String signupConfirmPassword) {

        System.out.println("signupAction");


        // Input Two Password is not equal
        if(!signupPassword.equals(signupConfirmPassword)) {
            return "redirect:/loginpage?error=3";
        }

        // Account name existed
        // return false for UserName existed, true for success signup
        if(!userService.registerUser(signupName, signupPassword)) {
            return "redirect:/loginpage?error=2";
        }

        return "redirect:/loginpage?success=true";
    }
}

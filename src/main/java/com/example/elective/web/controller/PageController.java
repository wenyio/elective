package com.example.elective.web.controller;

import com.example.elective.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static com.example.elective.constant.SystemConstant.ACTIVE_URL;
import static com.example.elective.constant.SystemConstant.USER_KEY;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2021/2/27
 * @see: com.example.elective.controller
 * @version: v1.0.0
 */
@Controller
public class PageController {

    @Resource
    private UserService userService;

    @Resource
    private HttpSession httpSession;

    @GetMapping(value = "/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String login() {
        if (httpSession.getAttribute(USER_KEY) != null) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    @GetMapping(value = "/dashboard")
    public String dashboard(Model model) {
        httpSession.setAttribute(ACTIVE_URL, "dashboard");
        return "dashboard";
    }

    @GetMapping(value = "/course")
    public String course() {
        httpSession.setAttribute(ACTIVE_URL, "course");
        return "course/list";
    }

    @GetMapping(value = "/course/save")
    public String courseSave() {
        httpSession.setAttribute(ACTIVE_URL, "course");
        return "course/add";
    }

    @GetMapping(value = "/user")
    public String user() {
        httpSession.setAttribute(ACTIVE_URL, "user");
        return "user/list";
    }

    @GetMapping(value = "/user/save")
    public String userSave() {
        httpSession.setAttribute(ACTIVE_URL, "user");
        return "user/add";
    }

    @GetMapping(value = "/elective")
    public String elective() {
        httpSession.setAttribute(ACTIVE_URL, "elective");
        return "elective/elective";
    }

    @GetMapping(value = "/elective/course")
    public String electiveResult() {
        httpSession.setAttribute(ACTIVE_URL, "elective");
        return "/elective/course";
    }

    @GetMapping(value = "/modify/password")
    public String modifyPassword() {
        httpSession.setAttribute(ACTIVE_URL, "profile");
        return "modify/password";
    }

    @GetMapping(value = "/profile")
    public String profile() {
        httpSession.setAttribute(ACTIVE_URL, "profile");
        return "profile";
    }
}

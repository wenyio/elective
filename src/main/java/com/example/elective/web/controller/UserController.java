package com.example.elective.web.controller;

import com.example.elective.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static com.example.elective.constant.SystemConstant.USER_KEY;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/IvenCc
 * @date: 2021/3/1
 * @see: com.example.elective.web.controller
 * @version: v1.0.0
 */
@Controller
public class UserController {

    @Resource
    private HttpSession httpSession;

    @GetMapping("/logOut")
    public String logOut() {
        httpSession.removeAttribute(USER_KEY);
        return "redirect:/login";
    }
}

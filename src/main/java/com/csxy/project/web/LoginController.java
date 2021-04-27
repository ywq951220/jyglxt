package com.csxy.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/backLoginView.html")
    public String login() {
        return "login";//地址指向login.html
    }

    @GetMapping("/login.html")
    public ModelAndView backLogin() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }
}

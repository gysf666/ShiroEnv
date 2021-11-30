package com.example.shiro.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
    public UserController() {
    }

    @PostMapping({"/doLogin"})
    public String doLoginPage(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam(name = "rememberme", defaultValue = "") String rememberMe) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password, rememberMe.equals("remember-me")));
            return "forward:/";
        } catch (AuthenticationException var6) {
            return "forward:/login";
        }
    }

    @RequestMapping({"/"})
    public String helloPage() {
        return "hello";
    }

    @RequestMapping({"/unauth"})
    public String errorPage() {
        return "error";
    }

    @RequestMapping({"/login"})
    public String loginPage() {
        return "login";
    }
}
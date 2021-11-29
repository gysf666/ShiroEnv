package com.example.shiro.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.AbstractRememberMeManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;

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
    public String helloPage(){
        return "hello";
    }

    @RequestMapping("/change")
    @ResponseBody
    public String change() {
        final byte[] newKey = "4ra1n_Big_HacKeR".getBytes(StandardCharsets.UTF_8);
        try {
            Field _key = AbstractRememberMeManager.class.getDeclaredField("DEFAULT_CIPHER_KEY_BYTES");
            _key.setAccessible(true);
            Field keyModifiersField = Field.class.getDeclaredField("modifiers");
            keyModifiersField.setAccessible(true);
            keyModifiersField.setInt(_key, _key.getModifiers() & ~Modifier.FINAL);
            _key.set(null, newKey);
            System.out.println("----------------------------------------------------------1");

            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            String[] names = attributes.getAttributeNames(0);
            System.out.println(names.length);
            System.out.println("----------------------------------------------------------2");

            // 只修改默认配置并不会生效
//            Field _key = AbstractRememberMeManager.class.getDeclaredField("DEFAULT_CIPHER_KEY_BYTES");
//            _key.setAccessible(true);
//            Field keyModifiersField = Field.class.getDeclaredField("modifiers");
//            keyModifiersField.setAccessible(true);
//            keyModifiersField.setInt(_key, _key.getModifiers() & ~Modifier.FINAL);
//            _key.set(null, newKey);
//
//            WebApplicationContext context = (WebApplicationContext) RequestContextHolder.currentRequestAttributes()
//                    .getAttribute("org.springframework.web.servlet.DispatcherServlet.CONTEXT", 0);
//            CookieRememberMeManager manager = context.getBean(CookieRememberMeManager.class);
//
//            Field _encrypt = AbstractRememberMeManager.class.getDeclaredField("encryptionCipherKey");
//            _encrypt.setAccessible(true);
//            _encrypt.set(manager,newKey);
//
//            Field _decrypt = AbstractRememberMeManager.class.getDeclaredField("decryptionCipherKey");
//            _decrypt.setAccessible(true);
//            _decrypt.set(manager,newKey);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ok";
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
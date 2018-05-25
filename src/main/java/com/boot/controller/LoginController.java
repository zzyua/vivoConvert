package com.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class LoginController {


    @RequestMapping("/login")
    public String doLogin(String userName, String password, HttpServletRequest request){
        log.info("开始登陆。。。。");
        return "redirect:home";

    }

}

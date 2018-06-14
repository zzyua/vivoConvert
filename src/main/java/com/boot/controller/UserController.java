package com.boot.controller;

import com.boot.model.SysUser;
import com.boot.security.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/logout.page")
    public String logout(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        request.getSession().invalidate();
        //TODO 权限控制
//        SecurityUtils.getSubject().logout();

        return "redirect:login.page";

//        response.sendRedirect("login.page");


    }

    @RequestMapping("/checklogin.page")
    public String login(HttpServletRequest request, HttpServletResponse response , Model model) throws IOException, ServletException {
        String username = request.getParameter("userName");
        String password = request.getParameter("password");

        SysUser sysUser = sysUserService.findByKeyword(username);
        String errorMsg = "";
        String ret = request.getParameter("ret");

        if (StringUtils.isBlank(username)) {
            errorMsg = "用户名不可以为空";
        } else if (StringUtils.isBlank(password)) {
            errorMsg = "密码不可以为空";
        } else if (sysUser == null) {
            errorMsg = "查询不到指定的用户";
//        } else if (!sysUser.getPassword().equals(MD5Util.encrypt(password))) {
        } else if (!sysUser.getPassword().equals(password)) {
            errorMsg = "用户名或密码错误";
        } else if (sysUser.getStatus() != 1) {
            errorMsg = "用户已被冻结，请联系管理员";
        } else {
            request.getSession().setAttribute("user", sysUser);

            if (StringUtils.isNotBlank(ret)) {
                //跳转到对应到页面
                return "redirect:"+ret;
            } else {
                //跳转到主页
                return "redirect:/";
            }
        }



        model.addAttribute("error" , errorMsg) ;
        model.addAttribute("username" , username) ;
        if (StringUtils.isNotBlank(ret)) {
            model.addAttribute("ret", ret);
        }
        return "forward:/login.page";
    }

    @RequestMapping("/login.page")
    public String  doLoging(){
        return "login" ;
    }
}

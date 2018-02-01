package com.boot.controller;

import com.boot.entity.ThymeafEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用thymeleaf 模版 Controller 示例。
 */
@Controller
public class TemplateDemo {


    @GetMapping("/thymeleafDemo")
    public String testThymeleaf(HttpServletRequest request, Model model){
        model.addAttribute("name","cxx");

        ThymeafEntity entity = new ThymeafEntity();
        entity.setName("cxx2");
        model.addAttribute("entity" , entity) ;

        model.addAttribute("datePlanted","datePlantedVal");

        return "thymeleaf";
    }
}

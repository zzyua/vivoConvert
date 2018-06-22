package com.boot.controller;

import com.boot.entity.UrlEntity;
import com.boot.exception.RestDymaicException;
import com.boot.exception.Result;
import com.boot.exception.ResultEnum;
import com.boot.exception.ResultUtil;
import com.boot.security.param.TestVo;
import com.boot.util.BeanValidator;
import com.boot.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * 测试controller
 */
@Controller
@Slf4j
public class TestController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/getAllUrl")
    @ResponseBody
    public Result getAllMapingUrls(){
        final StringBuilder  methodName = new StringBuilder() ;
        RequestMappingHandlerMapping bean = SpringUtil.getBean(RequestMappingHandlerMapping.class);
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo,HandlerMethod> map = bean.getHandlerMethods();
        List urlList = new ArrayList<>();
        map.keySet().forEach(info->{

            List patterns = new ArrayList(info.getPatternsCondition().getPatterns());
            List methods = new ArrayList(info.getMethodsCondition().getMethods()) ;
            for (int i = 0; i < patterns.size(); i++) {
                methodName.setLength(0);
                if(methods.size() != 0  ){
                    methodName.append(methods.get(i));
                }else {
                    methodName.append("") ;
                }
                UrlEntity entity= UrlEntity.builder().method(methodName.toString()).url((String) patterns.get(i)).build();
                urlList.add(entity);
            }

        });
        return  ResultUtil.success(urlList);
    }


    @RequestMapping("/hello.page")
    public  String helloPage( Model model){
        model.addAttribute("pagekey" , "hello") ;
        log.info("访问url是hello页面,pagekey:{}",model.containsAttribute("pagekey"));
        return "vivo/hello_test" ;

    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public Result validate(TestVo vo) throws Exception {
        log.info("validate");
        //1、
        BeanValidator.check(vo);

        return ResultUtil.success("test validate") ;
    }


    @GetMapping(value = "/hello_success.json")
    @ResponseBody
    public Result hello_success()  {
        log.info("say hello...");
        Locale locale = LocaleContextHolder.getLocale();

        String localeMessage = messageSource.getMessage("TEST_ERROR", null, locale);
        System.out.println("--------");
        System.out.println(localeMessage);
        return ResultUtil.success() ;
    }

    @GetMapping(value = "/hello_err.json")
    @ResponseBody
    public Result hello_error()  {
        log.info("say hello...");
        if(1==1)
            throw new RestDymaicException(ResultEnum.TEST_ERROR) ;
        return ResultUtil.success() ;
    }



    @GetMapping("/hello_succes.page")
    public String helloPageSuccess(){
        return "test";
    }

    @GetMapping("/hello_err.page")
    public String helloPageError(){
        if (2==2)
            throw new RestDymaicException(ResultEnum.TEST_ERROR) ;
        return "test";
    }



}

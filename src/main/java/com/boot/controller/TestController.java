package com.boot.controller;

import com.boot.exception.RestDymaicException;
import com.boot.exception.Result;
import com.boot.exception.ResultEnum;
import com.boot.exception.ResultUtil;
import com.boot.security.param.TestVo;
import com.boot.util.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试controller
 */
@Controller
@Slf4j
public class TestController {

    @RequestMapping("/hello.page")
    public  String helloPage( Model model){
        model.addAttribute("pagekey" , "hello") ;
        return "vivo/hello_test" ;

    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public Result validate(TestVo vo) throws Exception {
        log.info("validate");
        //1、
        BeanValidator.check(vo);
//        Map map = BeanValidator.validate(vo) ;
//        map.forEach((k,v)->{
//            log.info("{}->{}",k,v);
//        });

//        SysAclModuleMapper moduleMapper = SpringUtil.popBean(SysAclModuleMapper.class);
//        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
//        log.info(JsonMapper.obj2String(module));
//        BeanValidator.check(vo);
        return ResultUtil.success("test validate") ;
    }


    @GetMapping(value = "/hello_success.json")
    @ResponseBody
    public Result hello_success()  {
        log.info("say hello...");
        return ResultUtil.success() ;
    }

    @GetMapping(value = "/hello_err.json")
    @ResponseBody
    public Result hello_error()  {
        log.info("say hello...");
        if(1==1)
            throw new RestDymaicException(ResultEnum.TEST_ERROR) ;
        return ResultUtil.error(ResultEnum.TEST_ERROR) ;
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

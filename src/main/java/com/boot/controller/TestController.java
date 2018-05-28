package com.boot.controller;

import com.boot.exception.RestDymaicException;
import com.boot.exception.Result;
import com.boot.exception.ResultEnum;
import com.boot.exception.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;

/**
 * 测试controller
 */
@Controller
@Slf4j
public class TestController {


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

    @GetMapping("/hello_err")
    public String helloPageError2(){
        if (2==2)
            throw new RestDymaicException(ResultEnum.TEST_ERROR) ;
        return "test";
    }


}

package com.boot.exception;

import com.boot.entity.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常 处理类
 * @author  zhangzy
 */
@ControllerAdvice
//@RestControllerAdvice
public class ExceptionHandle {

    private  static  final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);


    /**
     * 处理 restFul的返回异常,主要返回是json对象
     * ResponseBody 的返回形式
     * @param e RestDymaicException
     * @return json
     */
    @ExceptionHandler(value = RestDymaicException.class)
    @ResponseBody
    public  static  Result handler(Exception e ){
        if(e instanceof  RestDymaicException){
            RestDymaicException dymaicException = (RestDymaicException) e;
            return ResultUtil.error(dymaicException.getCode(), dymaicException.getMessage());
        }else {
            logger.error("【系统异常】：{}",e.getMessage());
            return ResultUtil.error(ResultEnum.UNKNOW_ERROR);
        }

    }

    /**
     * 返回到指定到出错的界面
     * @param model Model
     * @param e ResolverDymaicException
     * @return 界面url
     */
    @ExceptionHandler(value = ResolverDymaicException.class)
    public  static String resolveException(Model model , Exception e ){
        if(e instanceof  ResolverDymaicException ){
            ResolverDymaicException resolverDymaicException = (ResolverDymaicException) e;
            model.addAttribute("msg",resolverDymaicException.getMessage());
        }
        return Common.ERROR;
    }

    /**
     * 处理
     * @param e  未正常抛出的异常Exception
     * @return
     */
    //TODO 这里需要实现 有@ResponseBody注解的controller的方法 和 没有被注解的时候，可以进入到不停的拦截方法
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public  static  Result handlere(Exception e ){
            logger.error("【系统异常】：{}",e);
            return ResultUtil.error(ResultEnum.UNKNOW_ERROR);
    }



}
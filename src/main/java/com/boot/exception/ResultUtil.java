package com.boot.exception;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 用于处理异常处理的工具类
 * @author zhangzy
 */
public class ResultUtil {

    public  static  Result success(Object object){
        Result result  = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static  Result success(){
        return  success(null);
    }

    public  static  Result error(Integer code , String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result  error(RestDymaicException exception){
        return  error(exception.getCode() , exception.getMessage());
    }

    public static Result error(ResultEnum resultEnum){
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMessage());
        return result;
    }







}

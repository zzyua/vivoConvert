package com.boot.exception;

/**
 *
 * 自定义异常类，必须继承RuntimeException
 * RestFull 返回异常
 * @author  zhangzy
 */
public class RestDymaicException extends  RuntimeException{


    private  Integer code ;

    public RestDymaicException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

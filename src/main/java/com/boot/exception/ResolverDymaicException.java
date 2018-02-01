package com.boot.exception;

/**
 *
 * 自定义异常类，必须继承RuntimeException
 * 返回视图解析异常页面
 * @author  zhangzy
 */
public class ResolverDymaicException extends  RuntimeException{


    private  Integer code ;

    public ResolverDymaicException(ResultEnum resultEnum) {
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

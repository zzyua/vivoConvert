package com.boot.exception;

/**
 * 统一定义 自定义异常的类型
 * @author zhangzy
 */
public enum  ResultEnum {

    TEST_ERROR(-111,"测试异常"),
    UNKNOW_ERROR(-1,"未知错误"),
    REQUESTURLVAILD(-2, "请求url异常，请检查")


    ;


    private Integer code ;

    private String message ;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

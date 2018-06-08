package com.boot.exception;

/**
 * 统一定义 自定义异常的类型
 * @author zhangzy
 */
public enum  ResultEnum {

    TEST_ERROR(-111,"测试异常"),
    UNKNOW_ERROR(-1,"未知错误"),
    REQUESTURLVAILD(-2, "请求url异常，请检查"),
    EXITSSAMENAMESYSDEPY(3, "同一层级下存在相同名称的部门"),
    NEEDDELETEDEPT_NOTEXITS(4, "待删除的部门不存在，无法删除"),
    CHILDDEPTS_EXITS(5,"当前部门下面有子部门，无法删除"),
    TELEPOHONENUM_EXITS(6,"电话已被占用"),
    EMAILNUM_EXITS(7,"邮箱已被占用"),
    NEEDUPDATEUSER_NOTEXITS(8,"待更新的用户不存在"),


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

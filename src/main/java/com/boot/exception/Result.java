package com.boot.exception;

import java.util.HashMap;
import java.util.Map;

public class Result<T> {

    /**
     * 状态吗
     */
    private Integer code ;

    /**
     * 信息
     */
    private String msg ;

    /**
     * 数据
     */
    private  T data ;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> toMap(){
        Map map = new HashMap() ;
        map.put("code" , code) ;
        map.put("msg" , msg) ;
        map.put("data" , data) ;
        return map;
    }
}

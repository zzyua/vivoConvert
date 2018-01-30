package com.boot.local;

import com.boot.util.StrUtil;

public class RootPathThreadLocal {


    private  static   ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getString(){

        if(StrUtil.isEmpty(threadLocal.get())){
            return  "D";
        }
        return threadLocal.get();
    }

    public  static void  set(String rootPath){
        threadLocal.set(rootPath);
    }


}

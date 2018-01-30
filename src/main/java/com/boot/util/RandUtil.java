package com.boot.util;

import java.util.Date;

/**
 * 获取随机数的一些方法
 */
public class RandUtil {

    /**
     * 获取到精确到秒的时间戳
     * @return
     */
    public  static  String getSecondTimestamp(){
        return  new Date().getTime()+"";
    }


    /**
     * 获取1到10的随机数
     * @return
     */
    public  static String getTenRandom(){
        return (int)(Math.random() * 10) + "";
    }

    /**
     * 生产文件目录，用于上传时，保证文件目录不唯一
     * @return
     */
    public static  String getFilePath(){
        return getSecondTimestamp() + getTenRandom();
    }

    public static void main(String[] args){
        System.out.println(getSecondTimestamp() + getTenRandom());
//        System.out.println(getTenRandom());
    }
}

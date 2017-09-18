package com.boot.util;

public class StrUtil {
	
	public static boolean isEmpty(String str){
		if(str == null || str.trim().length() == 0)
			return true;
		return false;
	}

}

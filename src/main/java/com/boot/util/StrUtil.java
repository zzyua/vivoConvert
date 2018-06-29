package com.boot.util;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;

public class StrUtil {
	
	public static boolean isEmpty(String str){
		if(str == null || str.trim().length() == 0)
			return true;
		return false;
	}

	public static List<Integer> splitToListInt(String str) {
		List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
		return strList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());
	}

}

package com.boot.util;

public class PoiStrUtil {
	



	public static String getEffectiveVal(String parStr){
		
		if(parStr!= null && !"".equals(parStr) ){
			
			return getFilterVal(parStr);
		}
		return "";
	}
	
	public static String getFilterVal(String parStr){
		if( "屏幕尺寸".equals(parStr) || "采购性质".equals(parStr) || "供货商".equals(parStr) || "智能机".equals(parStr)){
			return "";
		}
		return parStr.substring(parStr.indexOf("]")+1,parStr.length()) ; 
	}


}

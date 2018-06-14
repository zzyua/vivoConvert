package com.boot.util;

public class PoiStrUtil {


	/**
	 *
	 * @param parStr  单元格标题
	 * @return 如果是地区，返回过滤后的地区数据，如：[5080]上海- 》 上海 ； 反之返回 ""
	 */
	public static String getEffectiveVal(String parStr){
		if(!StrUtil.isEmpty(parStr) ){
			return getFilterVal(parStr);
		}
		return "";
	}

	/**
	 *
	 * @param parStr
	 * @return
	 */
	public static String getFilterVal(String parStr){

		if( "屏幕尺寸".equals(parStr) || "采购性质".equals(parStr) || "供货商".equals(parStr) || "智能机".equals(parStr)
			 || "颜色".equals(parStr) ||  "来源".equals(parStr) || "制式".equals(parStr)
			 || "一级类目".equals(parStr) || "二级类目".equals(parStr) || "三级类目".equals(parStr)
		){
			return "";
		}
		return parStr.substring(parStr.indexOf("]")+1,parStr.length()) ; 
	}

	public static void main(String[] args){
		System.out.println(getFilterVal("dddd"));
		System.out.println("]sss".indexOf("]"));
	}


}

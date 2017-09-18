package com.boot.entity;

import java.text.SimpleDateFormat;

public enum DateFormatPoi {


	Format;
	
	private SimpleDateFormat format = null ; 
	
	private SimpleDateFormat dateTimeFormate = null ;
	
	private DateFormatPoi(){
		format = new SimpleDateFormat("yyyy-MM");
		dateTimeFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	
	
	public SimpleDateFormat getFormat(){
		return format;
	}
	
	public SimpleDateFormat getDateTimeFormate(){
		return dateTimeFormate;
	}
	


}

package com.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 该版本提供了批量转换统计数据功能。
 * @since  v1版本
 */
@SpringBootApplication
public class VivoConvertApplication
//extends SpringBootServletInitializer
{
	

	//打war包修改点:需要继承SpringBootServletInitializer类
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(VivoConvertApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(VivoConvertApplication.class, args);
	}
	
	
}

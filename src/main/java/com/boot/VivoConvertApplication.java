package com.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 这是Master主干版本代码
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

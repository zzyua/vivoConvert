package com.boot;

import com.boot.util.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 这是Master主干版本代码
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.boot.security.dao")

public class VivoConvertApplication
//extends SpringBootServletInitializer
{
	

	//打war包修改点:需要继承SpringBootServletInitializer类
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(VivoConvertApplication.class);
//	}

	public static void main(String[] args) {
		ConfigurableApplicationContext app = SpringApplication.run(VivoConvertApplication.class, args);
		SpringUtil.setApplicationContext(app);
	}
	
	
}

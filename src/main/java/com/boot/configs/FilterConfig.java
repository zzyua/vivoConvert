package com.boot.configs;

import com.boot.filter.AclControlFilter;
import com.boot.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class FilterConfig {

    @Bean
    @Order(1)
    public FilterRegistrationBean filterDemo4Registration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //注入过滤器
        registration.setFilter(new LoginFilter());
        //拦截规则
        registration.addUrlPatterns("/sys/*");
        registration.addUrlPatterns("/admin/*");
        //过滤器名称
        registration.setName("DemoFilter");
        //是否自动注册 false 取消Filter的自动注册
//        registration.setEnabled(false);
        //过滤器顺序
        registration.setOrder(1);
        return registration;
    }

    @Bean
    @Order(2)
    public FilterRegistrationBean filterRegistrationBean(){
//        AclControlFilter

        FilterRegistrationBean registration = new FilterRegistrationBean();
        //注入过滤器
        registration.setFilter(new AclControlFilter());
        //拦截规则
        registration.addUrlPatterns("/sys/*");
        registration.addUrlPatterns("/admin/*");

        registration.addInitParameter("exclusionUrls" , "/sys/user/noAuth.page,/login.page");

        return registration;
    }


}

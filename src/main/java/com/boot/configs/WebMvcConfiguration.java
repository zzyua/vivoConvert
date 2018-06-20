package com.boot.configs;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.boot.common.HttpInterceptor;
import com.boot.exception.DymaicMvcExceptionResoler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;

@Configuration
/**
 * 配置mvc config
 */
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private RestTemplateBuilder builder;

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // 默认语言
        slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // 参数名
//        lci.setParamName("lang");
        return lci;
    }

    @Bean(name = "messageSource")
    public MessageSource initMessageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();

//        log.info(“baseName====>:” + this.basename);
        messageSource.setBasename("classpath:/messages");
//        messageSource.setBasenames();
        messageSource.setDefaultEncoding("UTF-8");
//        messageSource.setCacheMillis(cacheMillis);

//        String msg = messageSource.getMessage(“login.failure.msg”, null, Locale.CHINA);
//        log.info(“Msg====>” + msg);

        return messageSource;
    }






    @Bean
    public RestTemplate restTemplate() {
        return builder.setConnectTimeout(10000).setReadTimeout(60000).build();
    }

    /**
     * 异常解析 modelandView 发生异常时，如果是rest风格的，返回json
     * @return MappingJackson2JsonView
     */
    @Bean(name = "jsonView" )
    public MappingJackson2JsonView jsonView(){
        return  new MappingJackson2JsonView() ;
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
//        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/home").setViewName("fileupload");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }


    @Bean
    public HttpMessageConverters httpMessageConverters() {
        return new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
    }


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor()) ;
        registry.addInterceptor(localeChangeInterceptor());

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {


    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new DymaicMvcExceptionResoler()) ;

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }
}

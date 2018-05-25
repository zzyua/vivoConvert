package com.boot.exception;

import com.boot.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
/**
 * Springboot MVC 全局统一异常处理
 * .json: 统一处理Rest风格请求异常，返回json
 * .page: 统一处理界面请求，返回error页面
 * 默认返回json错误
 */
public class DymaicMvcExceptionResoler implements HandlerExceptionResolver{

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "System error";
        MappingJackson2JsonView jsonView = SpringUtil.getBean("jsonView", MappingJackson2JsonView.class);


        // 这里我们要求项目中所有请求json数据，都使用.json结尾
        if (url.endsWith(".json")) {
            if (ex instanceof RestDymaicException ) {
                mv = new ModelAndView(jsonView, ResultUtil.error((RestDymaicException)ex).toMap());
            } else {
                log.error("unknown json exception, url:" + url, ex);
                mv = new ModelAndView(jsonView, ResultUtil.error(ResultEnum.UNKNOW_ERROR).toMap());
            }
        } else if (url.endsWith(".page")){ // 这里我们要求项目中所有请求page页面，都使用.page结尾
            log.error("unknown page exception, url:" + url, ex);
            if (ex instanceof RestDymaicException ) {
                mv = new ModelAndView("error", ResultUtil.error((RestDymaicException)ex).toMap());
            } else {
                log.error("unknown json exception, url:" + url, ex);
                mv = new ModelAndView("error", ResultUtil.error(ResultEnum.UNKNOW_ERROR).toMap());
            }
        } else {
            log.error("unknow exception, url:" + url, ex);
            mv = new ModelAndView(jsonView, ResultUtil.error(ResultEnum.REQUESTURLVAILD).toMap());
        }

        return mv;
    }
}

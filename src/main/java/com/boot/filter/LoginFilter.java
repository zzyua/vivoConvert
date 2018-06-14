package com.boot.filter;


import com.boot.common.RequestHolder;
import com.boot.model.SysUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 将请求转换成HttpServletRequest 请求
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        SysUser user = (SysUser) req.getSession().getAttribute("user");
        if(user == null){
            String path="/login.page";
            resp.sendRedirect(path);
            return;
        }
        RequestHolder.add(user);
        RequestHolder.add(req);
        chain.doFilter(request,response);
        return;

    }

    @Override
    public void destroy() {

    }
}

package com.phicomm.product.manger.module.permission;

import com.phicomm.product.manger.enumeration.SessionKeyEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 拦截swagger-ui页面，需要登录
 * Created by yufei.liu on 2016/12/19.
 */
public class SwaggerUIFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 该过滤器只拦截swagger-ui.html
     *
     * @param request  请求
     * @param response 响应
     * @param chain    过滤器链
     * @throws IOException      可能会有异常
     * @throws ServletException 可能会有异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        Object object = session.getAttribute(SessionKeyEnum.SWAGGER_LOGIN_FLAG.getKeyName());
        if (object == null) {
            // 没有登陆
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

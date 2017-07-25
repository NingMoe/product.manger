package com.phicomm.product.manger.module.permission;

import com.google.common.base.Joiner;
import com.phicomm.product.manger.annotation.PublicInterface;
import com.phicomm.product.manger.enumeration.SessionKeyEnum;
import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.utils.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * 权限管理
 * Created by yufei.liu on 2017/5/27.
 */
public class PermissionFilter extends HandlerInterceptorAdapter {

    private final static Logger logger = Logger.getLogger(PermissionFilter.class);

    /**
     * 调用接口前拦截接口授权：
     * login已经拦截了没有登陆的接口，到这里已经登陆要不就是这些接口是公开的接口，不需要拦截
     * 这里我们只要将已经登陆但是权限不够的接口拦截下来就可以了
     * <p>
     * 只处理接口，页面不处理
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理的代理类
     * @return 是否通过
     * @throws Exception 难免会出现异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // swagger属于调试并且相当于admin，意味着开放一切接口权限
        HttpSession session = request.getSession();
        boolean userLoginFlag = session != null && session.getAttribute(SessionKeyEnum.LOGIN_FLAG.getKeyName()) != null;
        boolean swaggerLoginFlag = session != null && session.getAttribute(SessionKeyEnum.SWAGGER_LOGIN_FLAG.getKeyName()) != null;
        if (swaggerLoginFlag) {
            return true;
        }
        // 开放接口不需要授权管理
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        PublicInterface publicInterface = handlerMethod.getMethod().getAnnotation(PublicInterface.class);
        if (publicInterface != null) {
            return true;
        }
        FunctionPoint functionPoint = handlerMethod.getMethod().getAnnotation(FunctionPoint.class);
        if (functionPoint == null) {
            logger.error("interface must @PublicInterface or @FunctionPoint.");
            response.sendRedirect(HttpUtil.getBaseUrl(request) + "/login");
            return false;
        }
        if (functionPoint.mustLogin() && !userLoginFlag) {
            logger.error(String.format("interface %s must login.", request.getContextPath()));
            response.sendRedirect(HttpUtil.getBaseUrl(request) + "/login");
            return false;
        }
        // 已经登陆
        if (userLoginFlag) {
            Object object = session.getAttribute(SessionKeyEnum.USER_PERMISSIONS.getKeyName());
            if (!(object instanceof Set)) {
                logger.error("error.");
                response.sendRedirect(HttpUtil.getBaseUrl(request) + "/login");
                return false;
            }
            Set permissionList = (Set) object;
            String[] values = functionPoint.value();
            for (String value : values) {
                if (permissionList.contains(value)) {
                    return true;
                }
            }
            logger.error(String.format("you have not permission[%s], own [%s].",
                    Joiner.on(",").join(values), Joiner.on(",").join(permissionList)));
            response.sendRedirect(HttpUtil.getBaseUrl(request) + "/login");
            return false;
        }
        return true;
    }

}

package com.phicomm.product.manger.controller.h5;

import com.phicomm.product.manger.annotation.PublicInterface;
import com.phicomm.product.manger.enumeration.SessionKeyEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登陆功能页面操作
 * Created by yufei.liu on 2017/5/31.
 */
@Controller
public class LoginPageController {

    /**
     * 返回登陆页面
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    @ApiIgnore("登陆页面展示")
    @PublicInterface
    public ModelAndView showLoginPage(HttpSession session, HttpServletResponse response) throws IOException {
        boolean userLoginFlag = session != null && session.getAttribute(SessionKeyEnum.LOGIN_FLAG.getKeyName()) != null;
        boolean swaggerLoginFlag = session != null && session.getAttribute(SessionKeyEnum.SWAGGER_LOGIN_FLAG.getKeyName()) != null;
        if(userLoginFlag || swaggerLoginFlag) {
            response.sendRedirect("s7report");
            return null;
        }
        return new ModelAndView("login/login");
    }

}

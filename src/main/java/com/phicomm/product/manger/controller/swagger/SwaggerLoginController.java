package com.phicomm.product.manger.controller.swagger;

import com.google.common.base.Strings;
import com.phicomm.product.manger.annotation.PublicInterface;
import com.phicomm.product.manger.config.SwaggerPropertiesConfig;
import com.phicomm.product.manger.enumeration.SessionKeyEnum;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.utils.RandomValidateCodeUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * swagger-ui登陆页面
 * Created by yufei.liu on 2016/12/19.
 */
@Controller
public class SwaggerLoginController {

    private String username;

    private String password;

    /**
     * 是否是本地测试状态
     */
    private boolean local;

    @Autowired
    public SwaggerLoginController(SwaggerPropertiesConfig propertiesConfig) {
        Assert.notNull(propertiesConfig);
        this.username = propertiesConfig.getSwaggerLoginName();
        this.password = propertiesConfig.getSwaggerLoginPassword();
        this.local = "admin".equals(this.password);
    }

    /**
     * 展示登陆页面
     *
     * @return 页面
     */
    @RequestMapping(value = "swagger/login", method = RequestMethod.GET)
    @PublicInterface
    public ModelAndView showLoginPage() {
        return new ModelAndView("swagger/login");
    }

    /**
     * 请求验证码，并将验证码写到session中
     */
    @RequestMapping(value = "swagger/verify/code", method = RequestMethod.GET)
    @ApiOperation(value = "获取验证码", notes = "获取验证码", response = Void.class)
    @ResponseBody
    @PublicInterface
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
        randomValidateCode.getRandCode(request, response);
    }

    /**
     * 登陆验证
     */
    @RequestMapping(value = "swagger/login/check", method = RequestMethod.POST)
    @ApiOperation(value = "登陆验证", notes = "登陆验证", response = CommonResponse.class)
    @ResponseBody
    @PublicInterface
    public CommonResponse loginCheck(@RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     @RequestParam("verifyCode") String verifyCode,
                                     HttpSession session) {
        // 本次测试环境，直接成功
        if (local) {
            session.setAttribute(SessionKeyEnum.SWAGGER_LOGIN_FLAG.getKeyName(), username);
            return CommonResponse.ok();
        }
        String currentVerifyCode = (String) session.getAttribute(RandomValidateCodeUtil.RANDOM_CODE_KEY);
        if (Strings.isNullOrEmpty(currentVerifyCode)) {
            return CommonResponse.error();
        }
        if (this.username.equals(username)
                && this.password.equals(password)
                && currentVerifyCode.equalsIgnoreCase(verifyCode)) {
            session.setAttribute(SessionKeyEnum.SWAGGER_LOGIN_FLAG.getKeyName(), username);
            return CommonResponse.ok();
        }
        return CommonResponse.error();
    }
}

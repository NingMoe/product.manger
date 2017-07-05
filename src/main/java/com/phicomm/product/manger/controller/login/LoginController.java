package com.phicomm.product.manger.controller.login;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.annotation.PublicInterface;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.LoginException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.login.AccountModel;
import com.phicomm.product.manger.service.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登陆接口
 * Created by yufei.liu on 2017/5/31.
 */
@Controller
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
        Assert.notNull(this.loginService);
    }

    /**
     * 登陆接口
     */
    @RequestMapping(value = "login/check", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("登陆接口")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据异常", response = CommonResponse.class)
    })
    @PublicInterface
    public CommonResponse login(@RequestBody AccountModel accountModel) throws DataFormatException, LoginException {
        boolean success = loginService.login(accountModel);
        return CommonResponse.ok().setStatus(success ? 0 : 1);
    }

    /**
     * logout接口
     * <p>
     * 接口分为两大类接口：page、正常接口
     * 正常接口又分为两类：PublicInterface、FunctionPoint
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("logout接口")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据异常", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public CommonResponse logout() {
        loginService.logout();
        return CommonResponse.ok();
    }

}

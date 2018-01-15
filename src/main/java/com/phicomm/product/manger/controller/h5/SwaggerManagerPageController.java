package com.phicomm.product.manger.controller.h5;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.enumeration.SessionKeyEnum;
import com.phicomm.product.manger.exception.SwaggerProjectNotFoundException;
import com.phicomm.product.manger.model.user.AdminUserInfo;
import com.phicomm.product.manger.module.navigation.NavigationManger;
import com.phicomm.product.manger.utils.VelocityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

/**
 * swagger页面管理：账户
 * Created by wei.yang on 2017/7/19.
 */
@Controller
public class SwaggerManagerPageController {

    private NavigationManger navigationManger;

    @Autowired
    public SwaggerManagerPageController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    /**
     * swagger 项目管理
     */
    @RequestMapping(value = "swagger/project/manager", method = RequestMethod.GET)
    @ApiIgnore("swagger项目管理")
    @FunctionPoint(value = "common")
    public ModelAndView showBalanceOtaPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "swagger/swagger_manager.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("swaggerManager"));
        return modelAndView;
    }

    /**
     * swagger 项目信息编辑
     */
    @RequestMapping(value = "swagger/project/edit", method = RequestMethod.GET)
    @ApiIgnore("swagger 项目信息编辑")
    @FunctionPoint(value = "common")
    public ModelAndView editOrInsertProject(@RequestParam(required = false) String projectName,
                                            HttpSession session) throws SwaggerProjectNotFoundException {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "swagger/swagger_edit.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModel().put("projectName", projectName);
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("swaggerEdit"));
        return modelAndView;
    }
}

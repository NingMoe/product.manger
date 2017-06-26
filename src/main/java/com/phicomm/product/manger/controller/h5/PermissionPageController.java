package com.phicomm.product.manger.controller.h5;

import com.phicomm.product.manger.enumeration.SessionKeyEnum;
import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.model.table.AdminUserInfo;
import com.phicomm.product.manger.module.navigation.NavigationManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

/**
 * 返回权限管理页面
 * Created by yufei.liu on 2017/6/3.
 */
@Controller
public class PermissionPageController {

    private NavigationManger navigationManger;

    @Autowired
    public PermissionPageController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    /**
     * 返回权限管理页面
     */
    @RequestMapping(value = "permission", method = RequestMethod.GET)
    @ApiIgnore("permission")
    @FunctionPoint(value = "permissionManger")
    public ModelAndView showPermissionMangerPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("context", "permission/permission.vm");
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("permissionManger"));
        return modelAndView;
    }

}

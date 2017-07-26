package com.phicomm.product.manger.controller.h5;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.enumeration.SessionKeyEnum;
import com.phicomm.product.manger.model.user.AdminUserInfo;
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
 * 用户管理和权限管理
 * <p>
 * Created by yufei.liu on 2017/7/17.
 */
@Controller
public class UserPermissionMangerPageController {

    private NavigationManger navigationManger;

    @Autowired
    public UserPermissionMangerPageController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    /**
     * 返回用户管理页面
     */
    @RequestMapping(value = "user/manger/page", method = RequestMethod.GET)
    @ApiIgnore("用户管理页面")
    @FunctionPoint(value = "common")
    public ModelAndView showUserMangerPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("context", "user_manger/user_manger.vm");
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("userManger"));
        return modelAndView;
    }

    /**
     * 返回创建用户的页面
     */
    @RequestMapping(value = "user/manger/page/create/user", method = RequestMethod.GET)
    @ApiIgnore("创建用户的页面")
    @FunctionPoint(value = "common")
    public ModelAndView showUserMangerPageForCreateUser(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("context", "user_manger/user_manger_create.vm");
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("userMangerCreate"));
        return modelAndView;
    }

    /**
     * 查看用户列表页面
     */
    @RequestMapping(value = "user/manger/page/list", method = RequestMethod.GET)
    @ApiIgnore("查看用户列表页面")
    @FunctionPoint(value = "common")
    public ModelAndView showUserMangerPageForList(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("context", "user_manger/user_manger_list.vm");
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("userMangerList"));
        return modelAndView;
    }

    /**
     * 返回权限管理页面
     */
    @RequestMapping(value = "permission/manger/page", method = RequestMethod.GET)
    @ApiIgnore("权限管理页面")
    @FunctionPoint(value = "common")
    public ModelAndView showPermissionMangerPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("context", "permission_manger/permission_manger.vm");
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("permissionManger"));
        return modelAndView;
    }

}

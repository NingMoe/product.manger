package com.phicomm.product.manger.controller.h5;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.enumeration.SessionKeyEnum;
import com.phicomm.product.manger.model.user.AdminUserInfo;
import com.phicomm.product.manger.module.navigation.NavigationManger;
import com.phicomm.product.manger.utils.VelocityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

/**
 * blu页面
 *
 * @author wei.yang on 2017/7/17.
 */
@Controller
public class BalanceBluPageController {

    private NavigationManger navigationManger;

    @Autowired
    public BalanceBluPageController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    /**
     * 电子秤Blu升级
     */
    @RequestMapping(value = "balance/blu", method = RequestMethod.GET)
    @ApiIgnore("电子秤Blu升级")
    @FunctionPoint(value = "common")
    public ModelAndView showBalanceOtaPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "blu/balance_blu.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceBlu"));
        return modelAndView;
    }

    /**
     * 电子秤Blu固件列表
     */
    @RequestMapping(value = "balance/blu/list/test", method = RequestMethod.GET)
    @ApiIgnore("电子秤Blu升级")
    @FunctionPoint(value = "common")
    public ModelAndView showTestBalanceOtaStatusPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "blu/balance_blu_list_test.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceBluList"));
        return modelAndView;
    }

    /**
     * 电子秤Blu固件列表
     */
    @RequestMapping(value = "balance/blu/list/prod", method = RequestMethod.GET)
    @ApiIgnore("电子秤Blu升级")
    @FunctionPoint(value = "common")
    public ModelAndView showProdBalanceOtaStatusPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "blu/balance_blu_list_prod.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceBluList"));
        return modelAndView;
    }

    /**
     * 电子秤Blu服务器地址管理
     */
    @RequestMapping(value = "balance/blu/mac/manage", method = RequestMethod.GET)
    @ApiIgnore("电子秤Blu升级")
    @FunctionPoint(value = "common")
    public ModelAndView balanceOtaMacManage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "blu/balance_blu_mac.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceBluMac"));
        return modelAndView;
    }
}

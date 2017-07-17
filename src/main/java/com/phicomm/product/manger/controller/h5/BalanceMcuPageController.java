package com.phicomm.product.manger.controller.h5;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.enumeration.SessionKeyEnum;
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
 * mcu页面
 * Created by wei.yang on 2017/7/17.
 */
@Controller
public class BalanceMcuPageController {

    private NavigationManger navigationManger;

    @Autowired
    public BalanceMcuPageController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    /**
     * 电子秤Ota升级
     */
    @RequestMapping(value = "balance/mcu", method = RequestMethod.GET)
    @ApiIgnore("电子秤MCU升级")
    @FunctionPoint(value = "common")
    public ModelAndView showBalanceOtaPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "mcu/balance_mcu.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceMcu"));
        return modelAndView;
    }

    /**
     * 电子秤MCU固件列表
     */
    @RequestMapping(value = "balance/mcu/list/test", method = RequestMethod.GET)
    @ApiIgnore("电子秤MCU升级")
    @FunctionPoint(value = "common")
    public ModelAndView showTestBalanceOtaStatusPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "mcu/balance_mcu_list_test.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceMcuList"));
        return modelAndView;
    }

    /**
     * 电子秤MCU固件列表
     */
    @RequestMapping(value = "balance/mcu/list/prod", method = RequestMethod.GET)
    @ApiIgnore("电子秤MCU升级")
    @FunctionPoint(value = "common")
    public ModelAndView showProdBalanceOtaStatusPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "mcu/balance_mcu_list_prod.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceMcuList"));
        return modelAndView;
    }

    /**
     * 电子秤MCU服务器地址管理
     */
    @RequestMapping(value = "balance/mcu/mac/manage", method = RequestMethod.GET)
    @ApiIgnore("电子秤MCU升级")
    @FunctionPoint(value = "common")
    public ModelAndView balanceOtaMacManage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "mcu/balance_mcu_mac.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceMcuMac"));
        return modelAndView;
    }
}

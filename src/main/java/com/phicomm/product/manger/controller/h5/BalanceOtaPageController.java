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
 * ota page
 * Created by wei.yang on 2017/7/10.
 */
@Controller
public class BalanceOtaPageController {

    private NavigationManger navigationManger;

    @Autowired
    public BalanceOtaPageController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    /**
     * 电子秤Ota升级
     */
    @RequestMapping(value = "balance/ota", method = RequestMethod.GET)
    @ApiIgnore("电子秤Ota升级")
    @FunctionPoint(value = "common")
    public ModelAndView showBalanceOtaPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "ota/balance_ota.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceOta"));
        return modelAndView;
    }

    /**
     * 电子秤Ota固件列表
     */
    @RequestMapping(value = "balance/ota/list/test", method = RequestMethod.GET)
    @ApiIgnore("电子秤Ota升级")
    @FunctionPoint(value = "common")
    public ModelAndView showTestBalanceOtaStatusPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "ota/balance_ota_list_test.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceOtaList"));
        return modelAndView;
    }

    /**
     * 电子秤Ota固件列表
     */
    @RequestMapping(value = "balance/ota/list/prod", method = RequestMethod.GET)
    @ApiIgnore("电子秤Ota升级")
    @FunctionPoint(value = "common")
    public ModelAndView showProdBalanceOtaStatusPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "ota/balance_ota_list_prod.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceOtaList"));
        return modelAndView;
    }

    /**
     * 电子秤Ota服务器地址管理
     */
    @RequestMapping(value = "balance/ota/server/manage", method = RequestMethod.GET)
    @ApiIgnore("电子秤Ota升级")
    @FunctionPoint(value = "common")
    public ModelAndView balanceOtaServerManage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "ota/balance_ota_server_list_manage.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceOtaServer"));
        return modelAndView;
    }

    /**
     * 添加电子秤Ota服务器地址
     */
    @RequestMapping(value = "balance/ota/server/add", method = RequestMethod.GET)
    @ApiIgnore("电子秤Ota升级")
    @FunctionPoint(value = "common")
    public ModelAndView balanceServerAdd(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "ota/balance_ota_server_add.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceOtaServerAdd"));
        return modelAndView;
    }

    /**
     * 电子秤Ota服务器地址管理
     */
    @RequestMapping(value = "balance/ota/mac/manage", method = RequestMethod.GET)
    @ApiIgnore("电子秤Ota升级")
    @FunctionPoint(value = "common")
    public ModelAndView balanceOtaMacManage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "ota/balance_ota_mac.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceOtaMac"));
        return modelAndView;
    }
}

package com.phicomm.product.manger.controller.h5;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.enumeration.SessionKeyEnum;
import com.phicomm.product.manger.model.table.AdminUserInfo;
import com.phicomm.product.manger.module.navigation.NavigationManger;
import com.phicomm.product.manger.service.BalanceOtaService;
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

    private BalanceOtaService balanceOtaService;

    @Autowired
    public BalanceOtaPageController(NavigationManger navigationManger,
                                    BalanceOtaService balanceOtaService) {
        this.balanceOtaService=balanceOtaService;
        this.navigationManger = navigationManger;
        Assert.notNull(this.balanceOtaService);
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
        modelAndView.getModel().put("context", "upgrade/balance_ota.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceOta"));
        return modelAndView;
    }

    /**
     * 电子秤Ota固件列表
     */
    @RequestMapping(value = "balance/ota/list", method = RequestMethod.GET)
    @ApiIgnore("电子秤Ota升级")
    @FunctionPoint(value = "common")
    public ModelAndView showBalanceOtaStatusPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "upgrade/balance_ota_list.vm.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModel().put("balanceOtaInfoList",balanceOtaService.fetchOtaList("test"));
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceOtaList"));
        return modelAndView;
    }
}

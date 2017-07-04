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
 * 出货管理
 * Created by wei.yang on 2017/7/4.
 */
@Controller
public class BalanceShipmentController {

    private NavigationManger navigationManger;

    @Autowired
    public BalanceShipmentController(NavigationManger navigationManger){
        this.navigationManger=navigationManger;
        Assert.notNull(this.navigationManger);
    }

    /**
     * 查询MAC信息：激活位置、首次使用时间、绑定的成员数量
     */
    @RequestMapping(value = "balance/shipment/mac/status", method = RequestMethod.GET)
    @ApiIgnore("首页展示")
    @FunctionPoint(value = "common")
    public ModelAndView showBalanceMacStatus(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "statistic/balance_mac_status.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceMacStatus"));
        return modelAndView;
    }
}

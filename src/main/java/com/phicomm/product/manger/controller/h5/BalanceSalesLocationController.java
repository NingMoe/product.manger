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
 * 电子秤销售
 * Created by yufei.liu on 2017/6/23.
 */
@Controller
public class BalanceSalesLocationController {

    private NavigationManger navigationManger;

    @Autowired
    public BalanceSalesLocationController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    /**
     * 获取电子秤销售地理位置统计
     */
    @RequestMapping(value = "statistic/sales/location", method = RequestMethod.GET)
    @ApiIgnore("电子秤销售地理位置统计")
    @FunctionPoint(value = "common")
    public ModelAndView showSalesLocationPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "statistic/sales_location.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("salesLocationStatistic"));
        return modelAndView;
    }

    /**
     * 获取电子秤销售地理位置统计（大屏显示）
     */
    @RequestMapping(value = "statistic/sales/location/projection", method = RequestMethod.GET)
    @ApiIgnore("电子秤销售地理位置统计")
    @FunctionPoint(value = "common")
    public ModelAndView publicSalesLocation() {
        return new ModelAndView("statistic/balance_location_statistic_projection");
    }

}

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
 * @author yafei.hou
 * Created by yafei.hou on 2017/12/28.
 */
@Controller
public class BalanceDataSourcesController {

    private NavigationManger navigationManger;

    @Autowired
    public BalanceDataSourcesController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    /**
     * 数据来源统计报告数量统计
     */
    @RequestMapping(value = "measure/data/sourcePage", method = RequestMethod.GET)
    @ApiIgnore("首页展示")
    @FunctionPoint(value = "common")
    public ModelAndView showSourcePage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "statistic/balance_data_source.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("BalanceDataSource"));
        return modelAndView;
    }
}

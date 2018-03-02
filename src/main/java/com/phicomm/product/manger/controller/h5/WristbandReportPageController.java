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
 * Created by xiang.zhang on 2018/2/24.
 * @author xiang.zhang
 */
@Controller
public class WristbandReportPageController {

    private NavigationManger navigationManger;

    @Autowired
    public WristbandReportPageController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    @RequestMapping(value = "wristband_report/page", method = RequestMethod.GET)
    @ApiIgnore("手环手表激活与使用数据报告")
    @FunctionPoint(value = "common")
    public ModelAndView wristbandReportPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "wristband_report/wristband_report.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("wristband_report"));
        return modelAndView;
    }

    /**
     * 电子秤数量统计
     */
    @RequestMapping(value = "wristband/statistic/count/page", method = RequestMethod.GET)
    @ApiIgnore("首页展示")
    @FunctionPoint(value = "common")
    public ModelAndView showBalanceStatisticCount(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "wristband_report/wristband_count.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("wristbandStatisticCount"));
        return modelAndView;
    }

}

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
 * 用户活跃度埋点页面接口
 *
 * @author qiang.ren
 * @date 2017/12/28
 */
@Controller
public class BalanceTracePageController {

    private NavigationManger navigationManger;

    @Autowired
    public BalanceTracePageController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    @RequestMapping(value = "trace/user/activity", method = RequestMethod.GET)
    @ApiIgnore("所有用户活跃度页面展示")
    @FunctionPoint(value = "common")
    public ModelAndView userActivityPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "trace/trace_user_activity.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("traceUserActivity"));
        return modelAndView;
    }

    @RequestMapping(value = "trace/user/activity/sport", method = RequestMethod.GET)
    @ApiIgnore("运动用户活跃度页面展示")
    @FunctionPoint(value = "common")
    public ModelAndView sportUserActivityPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "trace/sport_trace_user_activity.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("sportTraceUserActivity"));
        return modelAndView;
    }

    @RequestMapping(value = "trace/user/activity/balance", method = RequestMethod.GET)
    @ApiIgnore("健康用户活跃度页面展示")
    @FunctionPoint(value = "common")
    public ModelAndView healthUserActivityPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "trace/balance_trace_user_activity.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("balanceTraceUserActivity"));
        return modelAndView;
    }

    /**
     * 获取设备分析页面
     *
     * @param session session
     * @return 页面
     */
    @RequestMapping(value = "balance/terminal/activity/line/chart", method = RequestMethod.GET)
    @ApiIgnore("用户活跃度页面展示")
    @FunctionPoint(value = "common")
    public ModelAndView terminalActivityPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "trace/balance_terminal_app.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("terminalLineChart"));
        return modelAndView;
    }
}

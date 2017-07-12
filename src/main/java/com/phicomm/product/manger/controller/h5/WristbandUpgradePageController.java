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
 * 手环固件升级的页面接口
 *
 * Created by yufei.liu on 2017/7/10.
 */
@Controller
public class WristbandUpgradePageController {

    private NavigationManger navigationManger;

    @Autowired
    public WristbandUpgradePageController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    /**
     * 获得手环固件升级的文件上传页面
     */
    @RequestMapping(value = "wristband/upgrade/page/file/upload", method = RequestMethod.GET)
    @ApiIgnore("手环固件升级的文件上传页面")
    @FunctionPoint(value = "common")
    public ModelAndView showWristbandUpgradeForFileUpload(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "fota/wristband/wristband_upgrade_file_upload.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("wristbandUpgradeForFileUpload"));
        return modelAndView;
    }

    /**
     * 获得手环固件测试环境列表
     */
    @RequestMapping(value = "wristband/upgrade/page/test/list", method = RequestMethod.GET)
    @ApiIgnore("获得手环固件测试环境列表")
    @FunctionPoint(value = "common")
    public ModelAndView showWristbandUpgradeForTestList(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "fota/wristband/wristband_upgrade_test_list.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("wristbandUpgradeForTestList"));
        return modelAndView;
    }

    /**
     * 获得手环固件生产环境列表
     */
    @RequestMapping(value = "wristband/upgrade/page/prod/list", method = RequestMethod.GET)
    @ApiIgnore("获得手环固件生产环境列表")
    @FunctionPoint(value = "common")
    public ModelAndView showWristbandUpgradeForProdList(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "fota/wristband/wristband_upgrade_prod_list.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("wristbandUpgradeForProdList"));
        return modelAndView;
    }

    /**
     * 展示触发固件升级的配置页面
     */
    @RequestMapping(value = "wristband/upgrade/page/trigger/config", method = RequestMethod.GET)
    @ApiIgnore("展示触发固件升级的配置页面")
    @FunctionPoint(value = "common")
    public ModelAndView showWristbandUpgradeForTriggerConfig(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "fota/wristband/wristband_upgrade_trigger_config.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("wristbandUpgradeForTriggerConfig"));
        return modelAndView;
    }

}

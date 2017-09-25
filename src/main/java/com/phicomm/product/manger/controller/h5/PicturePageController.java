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
 * Created by xiang.zhang on 2017/9/6.
 */
@Controller
public class PicturePageController {
    private NavigationManger navigationManger;

    @Autowired
    public PicturePageController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    /**
     * 展示图片上传页面
     */
    @RequestMapping(value = "picture/page", method = RequestMethod.GET)
    @ApiIgnore("展示图片上传页面")
    @FunctionPoint(value = "common")
    public ModelAndView showHomePagePage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "fota/picture/picture_upload.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("Picture"));
        return modelAndView;
    }

    @RequestMapping(value = "picture/upload/page/config", method = RequestMethod.GET)
    @ApiIgnore("展示图片配置页面")
    @FunctionPoint(value = "common")
    public ModelAndView showWristbandUpgradeForTriggerConfig(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "fota/picture/picture_config.vm");
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("pictureUploadForConfig"));
        return modelAndView;
    }

}


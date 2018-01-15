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
 * Created by xiang.zhang on 2017/9/6.
 */
@Controller
public class WatchPlatePicturePageController {
    private NavigationManger navigationManger;

    @Autowired
    public WatchPlatePicturePageController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    /**
     * 展示图片上传页面
     */
    @RequestMapping(value = "watchplate/picture/upload/page", method = RequestMethod.GET)
    @ApiIgnore("展示图片上传页面")
    @FunctionPoint(value = "common")
    public ModelAndView showHomePagePage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "watchplate/watchplate_picture_upload.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("watchplatePictureUpload"));
        return modelAndView;
    }

    @RequestMapping(value = "watchplate/picture/config/page", method = RequestMethod.GET)
    @ApiIgnore("展示图片配置页面")
    @FunctionPoint(value = "common")
    public ModelAndView showWatchplatePictureForConfig(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "watchplate/watchplate_picture_param_config.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("watchplatePictureParamForConfig"));
        return modelAndView;
    }

    @RequestMapping(value = "watchplate/picture/list/page", method = RequestMethod.GET)
    @ApiIgnore("展示图片列表页面")
    @FunctionPoint(value = "common")
    public ModelAndView showWatchplatePictureList(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "watchplate/watchplate_picture_list.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("watchplatePictureList"));
        return modelAndView;
    }

}


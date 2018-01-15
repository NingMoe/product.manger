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
 * 文章页面接口
 * <p>
 * Created by Qiang on 2017/8/6.
 */
@Controller
public class EssayPageController {

    private NavigationManger navigationManger;

    @Autowired
    public EssayPageController(NavigationManger navigationManger) {
        this.navigationManger = navigationManger;
        Assert.notNull(this.navigationManger);
    }

    @RequestMapping(value = "essay/add/prod", method = RequestMethod.GET)
    @ApiIgnore("文章新增页面展示(生产环境)")
    @FunctionPoint(value = "common")
    public ModelAndView showEssayAddProdPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "essay/essay_add_prod.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("essayAddProd"));
        return modelAndView;
    }

    @RequestMapping(value = "essay/add/test", method = RequestMethod.GET)
    @ApiIgnore("文章新增页面展示(测试环境)")
    @FunctionPoint(value = "common")
    public ModelAndView showEssayAddTestPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "essay/essay_add_test.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("essayAddTest"));
        return modelAndView;
    }

    @RequestMapping(value = "essay/list/prod", method = RequestMethod.GET)
    @ApiIgnore("文章列表页面展示(生产环境)")
    @FunctionPoint(value = "common")
    public ModelAndView showEssayListProdPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "essay/essay_list_prod.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("essayListProd"));
        return modelAndView;
    }

    @RequestMapping(value = "essay/list/test", method = RequestMethod.GET)
    @ApiIgnore("文章列表页面展示(测试环境)")
    @FunctionPoint(value = "common")
    public ModelAndView showEssayListTestPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("framework/main_layout");
        AdminUserInfo adminUserInfo = (AdminUserInfo) session.getAttribute(SessionKeyEnum.USER_INFO.getKeyName());
        modelAndView.getModel().put("context", "essay/essay_list_test.vm");
        modelAndView.getModelMap().put("uuid", VelocityUtil.getUUID());
        modelAndView.getModelMap().put("adminUserInfo", adminUserInfo);
        modelAndView.getModelMap().put("navigation", navigationManger.getNavigationModel("essayListTest"));
        return modelAndView;
    }
}

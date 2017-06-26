package com.phicomm.product.manger.controller.h5;

import com.phicomm.product.manger.annotation.PublicInterface;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 处理404
 * Created by yufei.liu on 2017/6/2.
 */
@Controller
public class PageNotFoundController {

    /**
     * 返回404页面
     */
    @RequestMapping(value = "404", method = RequestMethod.GET)
    @ApiIgnore("404")
    @PublicInterface
    public ModelAndView show404Page() {
        return new ModelAndView("404/404");
    }

}

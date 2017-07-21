package com.phicomm.product.manger.controller.h5;

import com.phicomm.product.manger.annotation.FunctionPoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 电子秤销售
 * Created by yufei.liu on 2017/6/23.
 */
@Controller
public class BalanceSalesStatisticPageController {

    /**
     * 获取电子秤销售地理位置统计（大屏显示）
     */
    @RequestMapping(value = "statistic/sales/statistic/projection", method = RequestMethod.GET)
    @ApiIgnore("电子秤销售地理位置统计")
    @FunctionPoint(value = "common")
    public ModelAndView publicSalesLocation() {
        return new ModelAndView("statistic/balance_sales_statistic_projection");
    }

}

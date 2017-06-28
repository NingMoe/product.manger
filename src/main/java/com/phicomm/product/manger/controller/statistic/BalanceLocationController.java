package com.phicomm.product.manger.controller.statistic;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.statistic.BalanceLocationStatistic;
import com.phicomm.product.manger.model.statistic.BalanceSaleNumber;
import com.phicomm.product.manger.service.BalanceSalesLocationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 获取地区分布的统计
 *
 * Created by yufei.liu on 2017/6/25.
 */
@Controller
public class BalanceLocationController {

    private final BalanceSalesLocationService balanceSalesLocationService;

    @Autowired
    public BalanceLocationController(BalanceSalesLocationService balanceSalesLocationService) {
        this.balanceSalesLocationService = balanceSalesLocationService;
        Assert.notNull(this.balanceSalesLocationService);
    }

    /**
     * 获取电子秤第一次激活电子秤的地理位置信息
     */
    @RequestMapping(value = "balance/location/statistic", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("用户反馈信息")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 3, message = "用户不存在", response = CommonResponse.class)
    })
    @FunctionPoint("common")
    public BalanceLocationStatistic getBalanceLocationStatistic() {
        return balanceSalesLocationService.getBalanceLocationStatistic();
    }

    /**
     * 获取电子秤销售数量
     */
    @RequestMapping(value = "balance/sales/number", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("用户反馈信息")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 3, message = "用户不存在", response = CommonResponse.class)
    })
    @FunctionPoint("common")
    public BalanceSaleNumber getBalanceSaleNumber() {
        return balanceSalesLocationService.getBalanceSaleNumber();
    }
}

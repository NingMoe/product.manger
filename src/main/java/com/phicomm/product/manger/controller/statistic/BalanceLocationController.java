package com.phicomm.product.manger.controller.statistic;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.statistic.BalanceLocationStatistic;
import com.phicomm.product.manger.model.statistic.BalanceSaleNumber;
import com.phicomm.product.manger.service.BalanceLocationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 获取地区分布的统计
 * <p>
 * Created by yufei.liu on 2017/6/25.
 */
@Controller
public class BalanceLocationController {

    private final BalanceLocationService balanceLocationService;

    @Autowired
    public BalanceLocationController(BalanceLocationService balanceLocationService) {
        this.balanceLocationService = balanceLocationService;
        Assert.notNull(this.balanceLocationService);
    }

    /**
     * 获取电子秤第一次激活电子秤的地理位置信息
     */
    @RequestMapping(value = "balance/location/statistic", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("用户反馈信息")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class)
    })
    @FunctionPoint("common")
    public BalanceLocationStatistic getBalanceLocationStatistic() {
        return balanceLocationService.getBalanceLocationStatistic();
    }

    /**
     * 获取电子秤销售数量
     */
    @RequestMapping(value = "balance/sales/number", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("用户反馈信息")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class)
    })
    @FunctionPoint("common")
    public BalanceSaleNumber getBalanceSaleNumber() {
        return balanceLocationService.getBalanceSaleNumber();
    }

    /**
     * 按月统计位置信息:类型包括lianbi和其它（默认其它都是走的非联璧）
     *
     * @return 数据
     */
    @RequestMapping(value = "balance/location/month")
    @ResponseBody
    @ApiOperation("位置信息统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Map.class)
    })
    @FunctionPoint("common")
    public Map<String, Integer> obtainLocationCountByMonth(@RequestParam int month,
                                                           @RequestParam String type) {
        return balanceLocationService.obtainLocationCountByMonth(month, type);
    }

    /**
     * 按天统计位置信息
     *
     * @return 数据
     */
    @RequestMapping(value = "balance/location/day")
    @ResponseBody
    @ApiOperation("位置信息统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Map.class)
    })
    @FunctionPoint("common")
    public Map<String, Integer> obtainLocationCountByDay(@RequestParam int day,
                                                         @RequestParam String type) {
        return balanceLocationService.obtainLocationCountByDay(day, type);
    }
}

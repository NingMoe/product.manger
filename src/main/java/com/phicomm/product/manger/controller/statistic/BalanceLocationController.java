package com.phicomm.product.manger.controller.statistic;

import com.phicomm.product.manger.annotation.PublicInterface;
import com.phicomm.product.manger.model.common.Response;
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
    @ApiOperation("获取电子秤第一次激活电子秤的地理位置信息")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
//    @FunctionPoint("common")
    @PublicInterface
    public Response<BalanceLocationStatistic> getBalanceLocationStatistic() {
        BalanceLocationStatistic balanceLocationStatistic = balanceLocationService.getBalanceLocationStatistic();
        return new Response<BalanceLocationStatistic>().setData(balanceLocationStatistic);
    }

    /**
     * 获取电子秤销售数量
     */
    @RequestMapping(value = "balance/sales/number", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("获取电子秤销售数量")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
//    @FunctionPoint("common")
    @PublicInterface
    public Response<BalanceSaleNumber> getBalanceSaleNumber() {
        BalanceSaleNumber balanceSaleNumber = balanceLocationService.getBalanceSaleNumber();
        return new Response<BalanceSaleNumber>().setData(balanceSaleNumber);
    }

    /**
     * 按月统计位置信息:类型包括lianbi和其它（默认其它都是走的非联璧）
     *
     * @return 数据
     */
    @RequestMapping(value = "balance/location/month", method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    @ApiOperation("按月统计位置信息:类型包括lianbi和其它（默认其它都是走的非联璧）")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
//    @FunctionPoint("common")
    @PublicInterface
    public Response<Map<String, Integer>> obtainLocationCountByMonth(@RequestParam int month,
                                                                     @RequestParam String type,
                                                                     @RequestParam int pageSize) {
        Map<String, Integer> locationInfo = balanceLocationService.obtainLocationCountByMonth(month, type, pageSize);
        return new Response<Map<String, Integer>>().setData(locationInfo);
    }

    /**
     * 按天统计位置信息
     *
     * @return 数据
     */
    @RequestMapping(value = "balance/location/day", method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    @ApiOperation("按天统计位置信息")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
//    @FunctionPoint("common")
    @PublicInterface
    public Response<Map<String, Integer>> obtainLocationCountByDay(@RequestParam int day,
                                                                   @RequestParam String type,
                                                                   @RequestParam int pageSize) {
        Map<String, Integer> locationInfo = balanceLocationService.obtainLocationCountByDay(day, type, pageSize);
        return new Response<Map<String, Integer>>().setData(locationInfo);
    }

}

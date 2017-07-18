package com.phicomm.product.manger.controller.statistic;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.statistic.BalanceMacStatus;
import com.phicomm.product.manger.service.BalanceStatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 统计数量信息
 * Created by wei.yang on 2017/6/30.
 */
@Controller
@Api(value = "统计某一信息在某个单位下的数量", description = "统计某一信息在某个单位下的数量")
public class BalanceStatisticController {

    private BalanceStatisticService balanceStatisticService;

    @Autowired
    public BalanceStatisticController(BalanceStatisticService balanceStatisticService) {
        this.balanceStatisticService = balanceStatisticService;
    }

    /**
     * 新增数量统计：不包含今天
     *
     * @return 数据
     */
    @RequestMapping(value = "balance/statistic/month")
    @ResponseBody
    @ApiOperation("新增信息统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Map.class)
    })
    @FunctionPoint("common")
    public Response<Map<String, Integer>> obtainCountByMonth(@RequestParam int month,
                                                             @RequestParam String type) {
        return new Response<Map<String, Integer>>().setData(balanceStatisticService.obtainCountByMonth(month, type));
    }

    /**
     * 新增数量统计：不包含今天
     *
     * @return 数据
     */
    @RequestMapping(value = "balance/statistic/day")
    @ResponseBody
    @ApiOperation("新增信息统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Map.class)
    })
    @FunctionPoint("common")
    public Response<Map<String, Integer>> obtainCountByDay(@RequestParam int day,
                                                           @RequestParam String type) {
        return new Response<Map<String, Integer>>().setData(balanceStatisticService.obtainCountByDay(day, type));
    }

    /**
     * 查找mac相关信息
     *
     * @return 数据
     */
    @RequestMapping(value = "balance/mac/info", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("mac地址信息")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @FunctionPoint("common")
    public Response<BalanceMacStatus> obtainBalanceMacInfo(@RequestParam String searchParam) throws DataFormatException {
        BalanceMacStatus balanceMacStatus = balanceStatisticService.obtainBalanceStatusInfo(searchParam);
        return new Response<BalanceMacStatus>().setData(balanceMacStatus);
    }
}

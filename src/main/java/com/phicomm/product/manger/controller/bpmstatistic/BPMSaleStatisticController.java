package com.phicomm.product.manger.controller.bpmstatistic;

import com.phicomm.product.manger.annotation.PublicInterface;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.service.BPMSaleStatisticService;
import io.swagger.annotations.Api;
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
 * 销量统计
 * Created by yafei.hou on 2017/11/9.
 */
@Controller
@Api(value = "血压计 销量统计", description = "血压计 销量统计")
public class BPMSaleStatisticController {

    private BPMSaleStatisticService bpmSaleStatisticService ;

    @Autowired
    public BPMSaleStatisticController(BPMSaleStatisticService bpmSaleStatisticService) {
        this.bpmSaleStatisticService = bpmSaleStatisticService;
        Assert.notNull(bpmSaleStatisticService);
    }

    /**
     * 血压计 销量统计 前n个月
     *
     * @return 数据
     */
    @RequestMapping(value = "bpm/statistic/sale/byMonth" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("血压计 销量统计 前n个月")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, Integer>> obtainBPMSaleNumByMonth(@RequestParam int month) {
        Map<String, Integer> statisticData = bpmSaleStatisticService.obtainBPMSaleNumByMonth(month);
        return new Response<Map<String, Integer>>().setData(statisticData);
    }

    /**
     * 血压计 销量统计 最近n天
     *
     * @return 数据
     */
    @RequestMapping(value = "bpm/statistic/sale/byDay" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("血压计 销量统计 前n个月")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, Integer>> obtainBPMSaleNumByDay(@RequestParam int day) {
        Map<String, Integer> statisticData = bpmSaleStatisticService.obtainBPMSaleNumByDay(day);
        return new Response<Map<String, Integer>>().setData(statisticData);
    }


    /**
     * 血压计 销售总量
     * @return 数量
     */
    @RequestMapping(value = "bpm/statistic/sale/all" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("血压计销售总量 ")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Integer> obtainBPMSaleNumAll() {
        Integer statisticData = bpmSaleStatisticService.obtainBPMSaleNumAll();
        return new Response<Integer>().setData(statisticData);
    }

    /**
     * 血压计 今天销售总量
     * @return 数量
     */
    @RequestMapping(value = "bpm/statistic/sale/today" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("血压计 今天销售总量 ")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Integer> obtainBPMSaleNumToday() {
        Integer statisticData = bpmSaleStatisticService.obtainBPMSaleNumToday();
        return new Response<Integer>().setData(statisticData);
    }
}


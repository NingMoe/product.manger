package com.phicomm.product.manger.controller.bpmstatistic;

import com.phicomm.product.manger.annotation.PublicInterface;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.service.BPMStatisticService;
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

import java.text.ParseException;
import java.util.Map;

/**
 * 血压计的绑定数量，以及查询数据
 * Created by yafei.hou on 2017/11/6.
 */
@Controller
@Api(value = "统计血压计绑定的数量", description = "统计血压计绑定的数量")
public class BPMStatisticController {

    private BPMStatisticService bpmStatisticService ;


    @Autowired
    public BPMStatisticController(BPMStatisticService bpmStatisticService) {
        this.bpmStatisticService = bpmStatisticService;
        Assert.notNull(bpmStatisticService);
    }

    /**
     * 最近N天的血压计绑定的数量 最近N天
     *
     * @return 数据
     */
    @RequestMapping(value = "bpm/statistic/bind/day" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("最近N天的血压计绑定的数量 最近N天")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, Integer>> obtainCountByDay(@RequestParam int day) {
        Map<String, Integer> statisticData = bpmStatisticService.obtainBPMCountByDay(day);
        return new Response<Map<String, Integer>>().setData(statisticData);
    }

    /**
     * 最近N月的血压计绑定的数量 最近N月
     *
     * @return 数据
     */
    @RequestMapping(value = "bpm/statistic/bind/month" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("最近N月的血压计绑定的数量 最近N月")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, Integer>> obtainCountByMonth(@RequestParam int month) {
        Map<String, Integer> statisticData = bpmStatisticService.obtainBPMCountByMonth(month);
        return new Response<Map<String, Integer>>().setData(statisticData);
    }

    /**
     * 血压计绑定的总数
     *
     * @return 数据
     */
    @RequestMapping(value = "bpm/statistic/bind/all" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("被绑定的血压计总数")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Integer> obtainCountBindAll() {
        Integer statisticData = bpmStatisticService.obtainBPMCountBindAll();
        return new Response<Integer>().setData(statisticData);
    }



    /**
     * 血压计测量数据量统计（总量）
     *
     * @return 数据
     */
    @RequestMapping(value = "bpm/statistic/measure/all" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("血压计测量数据量统计（总量）")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Integer> obtainMeasureCountAll() {
        Integer statisticData = bpmStatisticService.obtainBPMeasureCounts();
        return new Response<Integer>().setData(statisticData);
    }

    /**
     * 血压计测量数据量统计（当前月份、当天总量）
     *
     * @return 数据
     */
    @RequestMapping(value = "bpm/statistic/measure/todayOrMonth" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("血压计测量数据量统计（当前月份和当天总量）")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, Integer>> obtainMeasureCountTodayOrMonthAll() throws ParseException {
        Map<String, Integer> statisticData = bpmStatisticService.obtainBPMeasureCountsThisMonthOrToday();
        return new Response<Map<String, Integer>>().setData(statisticData);
    }
}

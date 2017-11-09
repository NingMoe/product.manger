package com.phicomm.product.manger.controller.bpmstatistic;

import com.phicomm.product.manger.annotation.PublicInterface;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.service.BloodPressureMeasureDataSizeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.logging.Logger;

/**
 * 血压计用户测量数据 相关统计
 * Created by yafei.hou on 2017/11/8.
 */
@Controller
@RequestMapping("bpm/measure/statistic")
@Api(value = "统计血压计的测量数量", description = "测量数据数量")
public class BloodPressureMeasureDataSizeController {


    private BloodPressureMeasureDataSizeService bloodPressureMeasureDataSizeService;

    @Autowired
    public BloodPressureMeasureDataSizeController(BloodPressureMeasureDataSizeService bloodPressureMeasureDataSizeService) {
        this.bloodPressureMeasureDataSizeService = bloodPressureMeasureDataSizeService;
    }

    /**
     * 最近12个月，每月血压计测量数据
     *
     * @return 数据
     */
    @RequestMapping(value = "/dataByMonth" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("最近12个月，每月血压计测量数据")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, Integer>> obtainBpmMeasureDataSizeByMonth() {
        Map<String, Integer> statisticData = bloodPressureMeasureDataSizeService.bpmMeasureDataSizeByMonth();
        return new Response<Map<String, Integer>>().setData(statisticData);
    }

    /**
     * 最近15个月，每天血压计测量数据
     *
     * @return 数据
     */
    @RequestMapping(value = "/dataByDay" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("最近15个月，每天血压计测量数据")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, Integer>> obtainBpmMeasureDataSizeByDay() {
        Map<String, Integer> statisticData = bloodPressureMeasureDataSizeService.bpmMeasureDataSizeByDay();
        return new Response<Map<String, Integer>>().setData(statisticData);
    }

    /**
     * 血压计测量数据24小时分布
     *
     * @return 数据
     */
    @RequestMapping(value = "/dataByHour" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("血压计测量数据24小时分布")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, Integer>> obtainBpmMeasureDataSizeByHour() {
        Map<String, Integer> statisticData = bloodPressureMeasureDataSizeService.bpmMeasureDataSizeByHour();
        return new Response<Map<String, Integer>>().setData(statisticData);
    }
}

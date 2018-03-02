package com.phicomm.product.manger.controller.wristband;

import com.phicomm.product.manger.annotation.PublicInterface;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.service.WristbandReportService;
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

import java.util.List;
import java.util.Map;

/**
 * Created by xiang.zhang on 2018/2/26.
 * @author xiang.zhang
 */
@Controller
@Api(value = "手环手表生产数据报告", description = "手环手表生产数据报告")
@RequestMapping("wristband/reports/")
public class WristbandController {

    private WristbandReportService wristbandReportService;

    @Autowired
    public WristbandController(WristbandReportService wristbandReportService){

        this.wristbandReportService = wristbandReportService;
        Assert.notNull(this.wristbandReportService);

    }

    /**
     * 插入联璧，万家两合作商手环手表的激活数据
     */
    @RequestMapping(value = "/addActivationData", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("插入联璧，万家两合作商手环手表的激活数据")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "日期不能为空", response = Response.class)
    })
    @PublicInterface
    public Response insertActivationData(@RequestParam String date,
                                         @RequestParam long lianbi,
                                         @RequestParam long wanjia) throws Exception {
        if (date == null) {
            return new Response().setStatus(2);
        }
        wristbandReportService.insertActivationData(date, lianbi, wanjia);
        return new Response();
    }

    /**
     * 获取累计新增K码激活量和联璧，万家两厂家新增K码激活总量
     */
    @RequestMapping(value = "/GetActivationAllNum", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取总的激活数量和联璧，万家每个厂家的激活总量")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> getActivationNum() {
        List<Map<String, Object>> result =wristbandReportService.getActivationNum();
        return new Response<List<Map<String, Object>>>().setData(result);
    }


    /**
     * 获取最近N天的手环手表的激活情况统计（每个厂商激活数量）
     * 暂时取最近30天
     */
    @RequestMapping(value = "/ActivationNumberDay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取最近N天的激活情况统计（每个厂商激活数量）")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> getActivationNumEveryDay()
            throws Exception {
        List<Map<String, Object>> result =wristbandReportService.getActivationNumEveryDay();
        return new Response<List<Map<String, Object>>>().setData(result);
    }


    /**
     * 获取某天联璧，万家合作商激活状况
     */
    @RequestMapping(value = "/ActivationNumByMates", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取某天联璧，万家合作商激活状况")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> getActivationStatisticDay(@RequestParam String date)
            throws Exception {
        if (date == null) {
            return new Response<>();
        }
        List<Map<String, Object>> result =wristbandReportService.getActivationStatisticDay(date);
        return new Response<List<Map<String, Object>>>().setData(result);
    }




    /**
     * 获取最近N月的手环手表的激活情况统计
     * 暂时取最近10个月
     */
    @RequestMapping(value = "/GetActivationNumberMonth", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取最近N月的手环手表的激活情况统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> getActivationNumberMonth()
            throws Exception {
        List<Map<String, Object>> result =wristbandReportService.getActivationNumMonth();
        return new Response<List<Map<String, Object>>>().setData(result);
    }

}

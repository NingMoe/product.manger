package com.phicomm.product.manger.controller.s7reports;

import com.phicomm.product.manger.annotation.PublicInterface;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.service.S7reportsService;
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
 * @author yafei.hou
 *         Created by yafei.hou on 2017/12/26.
 */
@Controller
@Api(value = "s7生产数据报告", description = "s7生产数据报告")
@RequestMapping("s7/reports/")
public class S7reportsController {

    private S7reportsService s7reportsService;

    @Autowired
    public S7reportsController(S7reportsService s7reportsService) {
        this.s7reportsService = s7reportsService;
        Assert.notNull(this.s7reportsService);
    }

    /**
     * 插入各个合作商S7的激活数据,
     */
    @RequestMapping(value = "/addActivationData", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("插入各个合作商S7的激活数据")
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
        s7reportsService.insertActivationData(date, lianbi, wanjia);
        return new Response();
    }

    /**
     * 添加某一天的用户活跃数,
     */
    @RequestMapping(value = "/ActiveUsersCount", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("添加某一天的用户活跃数")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "日期不能为空", response = Response.class)
    })
    @PublicInterface
    public Response insertActiveUsersCount(@RequestParam String date,
                                         @RequestParam long ios,
                                         @RequestParam long android) throws Exception {
        if (date == null) {
            return new Response().setStatus(2);
        }
        s7reportsService.insertActiveUsersCount(date, ios, android);
        return new Response();
    }


    /**
     * 获取最近N天的激活情况统计（每个厂商激活数量）
     */
    @RequestMapping(value = "/ActivationStatisticDay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取最近N天的激活情况统计（每个厂商激活数量）")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> obtainActivationStatisticEveryDay()
            throws Exception {
        List<Map<String, Object>> result =s7reportsService.obtainActivationStatisticEveryDay();
        return new Response<List<Map<String, Object>>>().setData(result);
    }

    /**
     * 最近N天每天激活总量
     */
    @RequestMapping(value = "/ActivationAllCountDay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("最近N天每天激活总量")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> obtainActivationStatisticByDay(@RequestParam int days)
            throws Exception {
        List<Map<String, Object>> result =s7reportsService.obtainActivationStatisticByDay(days);
        return new Response<List<Map<String, Object>>>().setData(result);
    }

    /**
     * 获取某天各个合作商激活状况
     */
    @RequestMapping(value = "/ActivationCountByMates", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取某天各个合作商激活状况")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> obtainActivationStatisticDay(@RequestParam String date)
            throws Exception {
        if (date == null) {
            return new Response<>();
        }
        List<Map<String, Object>> result =s7reportsService.obtainActivationStatisticDay(date);
        return new Response<List<Map<String, Object>>>().setData(result);
    }

    /**
     * 获取最近N月的激活情况统计
     */
    @RequestMapping(value = "/ActivationCountMonth", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取最近N月的激活情况统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> obtainActivationStatisticMonth()
            throws Exception {
        List<Map<String, Object>> result =s7reportsService.obtainActivationStatisticMonth();
        return new Response<List<Map<String, Object>>>().setData(result);
    }

    /**
     * 获取每个厂家激活总量
     */
    @RequestMapping(value = "/ActivationAllCountsEveryMate", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取每个厂家激活总量")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> obtainActivationAllCounts() {
        List<Map<String, Object>> result =s7reportsService.obtainActivationAllCounts();
        return new Response<List<Map<String, Object>>>().setData(result);
    }

    /**
     * 获取某天各个设备的用户活跃量
     */
    @RequestMapping(value = "/activeUsersCount", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取某天各个设备的用户活跃量")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> activeUsersCount(@RequestParam String date) throws Exception {
        if (date==null){
            return new Response<>();
        }
        List<Map<String, Object>> result =s7reportsService.activeUsersCount(date);
        return new Response<List<Map<String, Object>>>().setData(result);
    }

    /**
     * 获取最近N天用户活跃度
     */
    @RequestMapping(value = "/activeUsersCountDays", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取最近N天用户活跃度")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> activeUsersCountEveryDay() throws Exception {
        List<Map<String, Object>> result =s7reportsService.activeUsersCountEveryDay();
        return new Response<List<Map<String, Object>>>().setData(result);
    }

}

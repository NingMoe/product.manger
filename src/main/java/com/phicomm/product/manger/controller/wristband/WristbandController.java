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
                                         @RequestParam long wanjia,
                                         @RequestParam String type) throws Exception {
        if (date == null) {
            return new Response().setStatus(2);
        }
        wristbandReportService.insertActivationData(date, lianbi, wanjia,type);
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
     * 获取最近N天的W1的激活情况统计（每个厂商激活数量）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "W1/ActivationNumberDay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取最近N天的激活情况统计（每个厂商激活数量）")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> getW1ActivationNumEveryDay()
            throws Exception {
        List<Map<String, Object>> result =wristbandReportService.getW1ActivationNumEveryDay();
        return new Response<List<Map<String, Object>>>().setData(result);
    }

    /**
     * 获取最近N天的W1P的激活情况统计（每个厂商激活数量）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "W1P/ActivationNumberDay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取最近N天的激活情况统计（每个厂商激活数量）")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> getW1PActivationNumEveryDay()
            throws Exception {
        List<Map<String, Object>> result =wristbandReportService.getW1PActivationNumEveryDay();
        return new Response<List<Map<String, Object>>>().setData(result);
    }

    /**
     * 获取最近N天的W2的激活情况统计（每个厂商激活数量）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "W2/ActivationNumberDay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取最近N天的激活情况统计（每个厂商激活数量）")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> getW2ActivationNumEveryDay()
            throws Exception {
        List<Map<String, Object>> result =wristbandReportService.getW2ActivationNumEveryDay();
        return new Response<List<Map<String, Object>>>().setData(result);
    }


    /**
     * 获取最近N天的W2P的激活情况统计（每个厂商激活数量）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "W2P/ActivationNumberDay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取最近N天的激活情况统计（每个厂商激活数量）")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> getW2PActivationNumEveryDay()
            throws Exception {
        List<Map<String, Object>> result =wristbandReportService.getW2PActivationNumEveryDay();
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

    /**
     * 获取最近N月的W1的激活情况统计
     * 暂时取最近10个月
     */
    @RequestMapping(value = "W1/GetActivationNumberMonth", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取最近N月的W1的激活情况统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> getW1ActivationNumberMonth()
            throws Exception {
        List<Map<String, Object>> result =wristbandReportService.getW1ActivationNumMonth();
        return new Response<List<Map<String, Object>>>().setData(result);
    }

    /**
     * 获取最近N月的W1P的激活情况统计
     * 暂时取最近10个月
     */
    @RequestMapping(value = "W1P/GetActivationNumberMonth", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取最近N月的W1的激活情况统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> getW1PActivationNumberMonth()
            throws Exception {
        List<Map<String, Object>> result =wristbandReportService.getW1PActivationNumMonth();
        return new Response<List<Map<String, Object>>>().setData(result);
    }

    /**
     * 获取最近N月的W2的激活情况统计
     * 暂时取最近10个月
     */
    @RequestMapping(value = "W2/GetActivationNumberMonth", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取最近N月的W1的激活情况统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> getW2ActivationNumberMonth()
            throws Exception {
        List<Map<String, Object>> result =wristbandReportService.getW2ActivationNumMonth();
        return new Response<List<Map<String, Object>>>().setData(result);
    }

    /**
     * 获取最近N月的W2P的激活情况统计
     * 暂时取最近10个月
     */
    @RequestMapping(value = "W2P/GetActivationNumberMonth", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取最近N月的W1的激活情况统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<List<Map<String, Object>>> getW2PActivationNumberMonth()
            throws Exception {
        List<Map<String, Object>> result =wristbandReportService.getW2PActivationNumMonth();
        return new Response<List<Map<String, Object>>>().setData(result);
    }

    /**
     * 手环手表新增数量统计：不包含今天
     * 区分设备的类型
     * @return 数据
     */
    @RequestMapping(value = "/number/month")
    @ResponseBody
    @ApiOperation("新增信息统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, Integer>> getNumByMonth(@RequestParam int month,
                                                             @RequestParam String type) {
        Map<String, Integer> statisticData = wristbandReportService.getNumByMonth(month, type);
        return new Response<Map<String, Integer>>().setData(statisticData);
    }

    /**
     * 手环手表新增数量统计：不包含今天
     * 不区分设备的类型
     * @return 数据
     */
    @RequestMapping(value = "total/number/month")
    @ResponseBody
    @ApiOperation("新增信息统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, Integer>> getTotalNumByMonth(@RequestParam int month) {
        Map<String, Integer> statisticData = wristbandReportService.getTotalNumByMonth(month);
        return new Response<Map<String, Integer>>().setData(statisticData);
    }

    /**
     * 手环手表新增数量统计：不包含今天
     * @return 数据
     */
    @RequestMapping(value = "/number/day")
    @ResponseBody
    @ApiOperation("新增信息统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, Integer>> getNumByDay(@RequestParam int day,
                                                      @RequestParam String type) {
        Map<String, Integer> statisticData = wristbandReportService.getNumByDay(day, type);
        return new Response<Map<String, Integer>>().setData(statisticData);
    }

    /**
     * 近30天手环手表的按地区信息统计使用量
     * 区分设备的类型统计
     * @return 数据
     */
    @RequestMapping(value = "location/day", method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    @ApiOperation("按天统计地区信息")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, Integer>> getLocationNumByDay(@RequestParam int day,
                                                              @RequestParam String type,
                                                              @RequestParam int pageSize) {
        Map<String, Integer> locationInfo = wristbandReportService.getLocationNumByDay(day, type, pageSize);
        return new Response<Map<String, Integer>>().setData(locationInfo);
    }


    /**
     * 近30天运动设备的按地区信息统计使用量
     * 不区分设备的类型统计
     * @return 数据
     */
    @RequestMapping(value = "total/location/day", method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    @ApiOperation("按天统计地区信息")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @PublicInterface
    public Response<Map<String, Integer>> getTotalLocationNumByDay(@RequestParam int day,
                                                              @RequestParam int pageSize) {
        Map<String, Integer> locationInfo = wristbandReportService.getTotalLocationNumByDay(day,pageSize);
        return new Response<Map<String, Integer>>().setData(locationInfo);
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
    public Response<List<Map<String, Object>>> getActivationStatisticByDay(@RequestParam int days)
            throws Exception {
        List<Map<String, Object>> result =wristbandReportService.getActivationStatisticByDay(days);
        return new Response<List<Map<String, Object>>>().setData(result);
    }
}

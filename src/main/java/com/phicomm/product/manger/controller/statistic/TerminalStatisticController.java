package com.phicomm.product.manger.controller.statistic;

import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.PlatformNotExistException;
import com.phicomm.product.manger.exception.TerminalStatisticTypeNotSupportException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.terminal.*;
import com.phicomm.product.manger.service.TerminalStatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 设备信息分析
 *
 * @author wei.yang on 2017/12/28
 */
@Controller
@Api("用户设备信息分析")
@RequestMapping(value = "terminal/")
public class TerminalStatisticController {

    private TerminalStatisticService terminalStatisticService;

    @Autowired
    public TerminalStatisticController(TerminalStatisticService terminalStatisticService) {
        this.terminalStatisticService = terminalStatisticService;
    }

    /**
     * 获取设备平台与渠道信息
     *
     * @param timeEntity 检索条件
     * @return 数据
     */
    @RequestMapping(value = "history/table/data", method = POST, consumes = "application/json",
            produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = Response.class)
    })
    @ApiOperation("获取设备、渠道、运行商等信息")
    @ResponseBody
    public Response<List<HistoryResultEntity>> obtainHistoryDate(@RequestBody SearchWithCertainTimeEntity timeEntity)
            throws DataFormatException {
        List<HistoryResultEntity> result = terminalStatisticService.obtainHistoryData(timeEntity);
        return new Response<List<HistoryResultEntity>>().setData(result);
    }

    /**
     * 数据种类列表
     *
     * @param entity 查询信息
     * @return 数据
     * @throws TerminalStatisticTypeNotSupportException 分析类型不存在
     * @throws PlatformNotExistException                平台不支持
     * @throws DataFormatException                      数据格式错误
     */
    @RequestMapping(value = "line/chart/data", method = POST, consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = Response.class),
            @ApiResponse(code = 26, message = "平台不支持", response = Response.class),
            @ApiResponse(code = 27, message = "分析类型不支持", response = Response.class)
    })
    @ApiOperation("获取某个平台、某个数据类型的线图数据")
    @ResponseBody
    public Response<Map<String, List<Integer>>> obtainLineChartData(@RequestBody PeriodWithPlatformEntity entity)
            throws TerminalStatisticTypeNotSupportException, PlatformNotExistException, DataFormatException {
        Map<String, List<Integer>> result = terminalStatisticService.obtainLineChartData(entity);
        return new Response<Map<String, List<Integer>>>().setData(result);
    }

    /**
     * 获取设备平台与渠道信息
     *
     * @param pageEntity 页面信息
     * @return 数据
     * @throws TerminalStatisticTypeNotSupportException 分析类型不存在
     * @throws PlatformNotExistException                平台不支持
     * @throws DataFormatException                      数据格式错误
     */
    @RequestMapping(value = "detail/page", method = POST, consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = Response.class),
            @ApiResponse(code = 26, message = "平台不支持", response = Response.class),
            @ApiResponse(code = 27, message = "分析类型不支持", response = Response.class)
    })
    @ApiOperation("获取设备与渠道信息,页码从0开始")
    @ResponseBody
    public Response<List<StatisticEntity>> obtainDetailByPage(@RequestBody PageWithPlatformEntity pageEntity)
            throws TerminalStatisticTypeNotSupportException, PlatformNotExistException, DataFormatException {
        List<StatisticEntity> result = terminalStatisticService.obtainPageStatisticDetail(pageEntity);
        return new Response<List<StatisticEntity>>().setData(result);
    }

    /**
     * 获取设备平台与渠道信息
     *
     * @param periodModel 查询条件：时间段
     * @return 数据
     * @throws TerminalStatisticTypeNotSupportException 分析类型不存在
     * @throws PlatformNotExistException                平台不支持
     * @throws DataFormatException                      数据格式错误
     */
    @RequestMapping(value = "detail/time", method = POST, consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = Response.class),
            @ApiResponse(code = 26, message = "平台不支持", response = Response.class),
            @ApiResponse(code = 27, message = "分析类型不支持", response = Response.class)
    })
    @ApiOperation("按时间段来获取设备与渠道信息")
    @ResponseBody
    public Response<List<StatisticEntity>> obtainDetailBetweenTime(@RequestBody PeriodWithPlatformEntity periodModel)
            throws TerminalStatisticTypeNotSupportException, PlatformNotExistException, DataFormatException {
        List<StatisticEntity> result = terminalStatisticService.obtainPeriodStatisticDetail(periodModel);
        return new Response<List<StatisticEntity>>().setData(result);
    }

    /**
     * 获取设备平台与渠道信息
     *
     * @return 设备信息
     */
    @RequestMapping(value = "history/detail/sync", method = POST, consumes = "application/json",
            produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class)
    })
    @ApiOperation("获取设备与渠道信息")
    @ResponseBody
    public CommonResponse syncHistoryData() {
        terminalStatisticService.syncAllData();
        return CommonResponse.ok();
    }
}

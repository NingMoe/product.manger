package com.phicomm.product.manger.controller.statistic;

import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.terminal.PageWithPlatformEntity;
import com.phicomm.product.manger.model.terminal.StatisticEntity;
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
     * @return 设备信息
     */
    @RequestMapping(value = "channel/detail/page", method = POST, consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @ApiOperation("获取设备与渠道信息")
    @ResponseBody
    public Response<List<StatisticEntity>> obtainTerminalDetail(@RequestBody PageWithPlatformEntity pageEntity) {
        List<StatisticEntity> result = terminalStatisticService.obtainPageStatisticDetail(pageEntity);
        return new Response<List<StatisticEntity>>().setData(result);
    }

    /**
     * 获取设备平台与渠道信息
     *
     * @return 设备信息
     */
    @RequestMapping(value = "channel/detail/sync", method = POST, consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @ApiOperation("获取设备与渠道信息")
    @ResponseBody
    public CommonResponse obtainTerminalDetail() {
        terminalStatisticService.syncYesterdayData();
        return CommonResponse.ok();
    }
}

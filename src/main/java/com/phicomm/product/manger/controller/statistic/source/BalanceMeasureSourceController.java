package com.phicomm.product.manger.controller.statistic.source;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.service.BalanceMeasureSourceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author yafei.hou
 *         Created by yafei.hou on 2017/12/28.
 */
@Controller
public class BalanceMeasureSourceController {

    private BalanceMeasureSourceService balanceMeasureSourceService;

    @Autowired
    public BalanceMeasureSourceController(BalanceMeasureSourceService balanceMeasureSourceService) {
        this.balanceMeasureSourceService = balanceMeasureSourceService;
        Assert.notNull(this.balanceMeasureSourceService);
    }

    /**
     * 获取最近10天内的数据来源信息
     *
     * @return 响应
     */
    @RequestMapping(value = "measure/data/source", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("获取最近10天内的数据来源信息")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class)
    })
    @FunctionPoint("common")
    public Response<List<Map<String, Object>>> getMeasureSources() {
        List<Map<String, Object>> result = balanceMeasureSourceService.getMeasureSources();
        return new Response<List<Map<String, Object>>>().setData(result);
    }
}

package com.phicomm.product.manger.controller.trace;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.trace.UserActivityInputInfo;
import com.phicomm.product.manger.model.trace.UserActivityTrace;
import com.phicomm.product.manger.service.UserActivityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户活跃度接口
 *
 * @author qiang.ren
 * @date 2017/12/29
 */
@Controller
public class UserActivityController {

    private UserActivityService userActivityService;

    @Autowired
    public UserActivityController(UserActivityService userActivityService) {
        this.userActivityService = userActivityService;
        Assert.notNull(this.userActivityService);
    }

    /**
     * 统计24小时用户活跃度（PV）
     *
     * @param userActivityInputInfo 用户活跃度传入信息
     * @return 24小时用户活跃度
     */
    @RequestMapping(value = "trace/user/activity/pv", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("统计24小时用户活跃度")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = CommonResponse.class)
    })
    @FunctionPoint("common")
    public Response<UserActivityTrace> traceUserActivityPV(@RequestBody UserActivityInputInfo userActivityInputInfo)
            throws DataFormatException {
        UserActivityTrace userActivityTrace = userActivityService.traceUserActivityPV(userActivityInputInfo);
        return new Response<UserActivityTrace>().setData(userActivityTrace);
    }

    /**
     * 统计24小时用户活跃度（UV）
     *
     * @param userActivityInputInfo 用户活跃度传入信息
     * @return 24小时用户活跃度
     */
    @RequestMapping(value = "trace/user/activity/uv", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("统计24小时用户活跃度")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class),
            @ApiResponse(code = 2, message = "数据格式错误", response = CommonResponse.class)
    })
    @FunctionPoint("common")
    public Response<UserActivityTrace> traceUserActivityUV(@RequestBody UserActivityInputInfo userActivityInputInfo)
            throws DataFormatException{
        UserActivityTrace userActivityTrace = userActivityService.traceUserActivityUV(userActivityInputInfo);
        return new Response<UserActivityTrace>().setData(userActivityTrace);
    }
}

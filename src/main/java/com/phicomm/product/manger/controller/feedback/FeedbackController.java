package com.phicomm.product.manger.controller.feedback;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.FeedbackNotFoundException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.feedback.*;
import com.phicomm.product.manger.service.BalanceFeedbackService;
import io.swagger.annotations.Api;
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

import java.util.List;


/**
 * 反馈信息接口
 *
 * @author qiang.ren
 * @date 2017/11/2
 */
@Controller
@Api(value = "反馈信息接口", description = "反馈信息接口")
public class FeedbackController {

    private BalanceFeedbackService feedbackService;

    @Autowired
    public FeedbackController(BalanceFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
        Assert.notNull(this.feedbackService);
    }

    /**
     * 获取反馈列表(带条件)
     *
     * @param historyRequestBean 要请求的数据
     * @return 反馈列表
     * @throws DataFormatException 数据格式错误
     */
    @RequestMapping(value = "feedback/list/filter", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = CommonResponse.class)
    })
    @ResponseBody
    @ApiOperation("后台管理获取反馈列表:maxId为-1的时候表示获取最新数据")
    @FunctionPoint(value = "common")
    public Response<List<FeedbackWithUserInfo>> fetchFeedbackList(@RequestBody HistoryRequestBean historyRequestBean)
            throws DataFormatException {
        List<FeedbackWithUserInfo> feedbackWithUserInfoList = feedbackService.fetchFeedbackList(historyRequestBean);
        return new Response<List<FeedbackWithUserInfo>>().setData(feedbackWithUserInfoList);
    }

    /**
     * 获取反馈列表（不带条件）
     *
     * @param maxIdBean 要请求的数据
     * @return 反馈列表
     */
    @RequestMapping(value = "feedback/list", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = CommonResponse.class)
    })
    @ResponseBody
    @ApiOperation("后台管理获取反馈列表:maxId为-1的时候表示获取最新数据")
    @FunctionPoint(value = "common")
    public Response<List<FeedbackWithUserInfo>> fetchFeedbackListWithoutFilter(@RequestBody MaxIdBean maxIdBean) {
        List<FeedbackWithUserInfo> feedbackWithUserInfoList = feedbackService.fetchFeedbackWithoutFilter(maxIdBean);
        return new Response<List<FeedbackWithUserInfo>>().setData(feedbackWithUserInfoList);
    }

    /**
     * 客服去获取某个问题的锁定状态
     *
     * @param requestBean 请求数据
     * @return 反馈的状态
     * @throws DataFormatException       数据格式异常
     * @throws FeedbackNotFoundException 反馈意见不存在
     */
    @RequestMapping(value = "feedback/status", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = CommonResponse.class),
            @ApiResponse(code = 24, message = "反馈意见不存在", response = CommonResponse.class)
    })
    @ResponseBody
    @ApiOperation("获取反馈意见的状态:客服")
    @FunctionPoint(value = "common")
    public Response<FeedbackLockStatusBean> obtainFeedbackStatus(@RequestBody FeedbackStatusRequestBean requestBean)
            throws DataFormatException, FeedbackNotFoundException {
        FeedbackLockStatusBean feedbackLockStatusBean = feedbackService.obtainFeedbackStatus(requestBean);
        return new Response<FeedbackLockStatusBean>().setData(feedbackLockStatusBean);
    }
}

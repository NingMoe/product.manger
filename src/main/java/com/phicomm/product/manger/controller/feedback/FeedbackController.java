package com.phicomm.product.manger.controller.feedback;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.table.FeedbackInfoWithBLOBs;
import com.phicomm.product.manger.model.table.FeedbackRequestBean;
import com.phicomm.product.manger.model.table.FeedbackReview;
import com.phicomm.product.manger.service.BalanceFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 接收客户端发送的反馈信息
 * <p>
 * Created by yufei.liu on 2017/2/22.
 */
@Controller
@Api(value = "接收客户端发送的反馈信息", description = "接收客户端发送的反馈信息")
public class FeedbackController {

    private BalanceFeedbackService feedbackService;

    @Autowired
    public FeedbackController(BalanceFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
        Assert.notNull(this.feedbackService);
    }

    /**
     * 接收用户的反馈数据，直接插到数据库
     *
     * @return 响应
     */
    @RequestMapping(value = "balance/feedback/fetch", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation("用户反馈信息")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public Response<List<FeedbackInfoWithBLOBs>> fetchFeedback(@RequestParam int pageSize,
                                                               @RequestParam int startId) {
        List<FeedbackInfoWithBLOBs> feedbackInfoWithBLOBsList = feedbackService.fetchFeedback(pageSize, startId);
        return new Response<List<FeedbackInfoWithBLOBs>>().setData(feedbackInfoWithBLOBsList);
    }

    /**
     * 接收用户的反馈数据，直接插到数据库
     *
     * @return 响应
     */
    @RequestMapping(value = "balance/feedback/fetch/v2", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("用户反馈信息")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public Response<List<FeedbackInfoWithBLOBs>> fetchFeedbackV2(@RequestBody FeedbackRequestBean feedbackRequestBean)
            throws DataFormatException {
        return new Response<List<FeedbackInfoWithBLOBs>>().setData(feedbackService.fetchFeedbackV2(feedbackRequestBean));
    }

    /**
     * 创建用户反馈的回复
     */
    @RequestMapping(value = "balance/feedback/review/create", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation("创建回复")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public void createFeedbackReview(@RequestParam long userId,
                                     @RequestParam long feedbackId,
                                     @RequestParam String reply) {
        FeedbackReview feedbackReview = new FeedbackReview(userId, feedbackId, reply, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        feedbackService.createFeedbackReview(feedbackReview);
    }

    /**
     * 查看用户反馈的回复
     *
     * @return 响应
     */
    @RequestMapping(value = "balance/feedback/review/list", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation("查看回复")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public Response<List<FeedbackReview>> listFeedbackReview(@RequestParam long feedbackId) {
        return new Response<List<FeedbackReview>>().setData(feedbackService.listFeedbackReview(feedbackId));
    }

    /**
     * 删除用户反馈的回复
     */
    @RequestMapping(value = "balance/feedback/review/delete", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation("删除回复")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public void deleteFeedbackReview(@RequestParam long feedbackReviewId) {
        feedbackService.deleteFeedbackReview(feedbackReviewId);
    }

    /**
     * 收藏用户反馈
     */
    @RequestMapping(value = "balance/feedback/collect", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation("收藏用户反馈")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public void collectFeedback(@RequestParam long feedbackId) {
        feedbackService.collectFeedback(feedbackId);
    }

    /**
     * 取消收藏用户反馈
     */
    @RequestMapping(value = "balance/feedback/unCollect", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation("取消收藏用户反馈")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class)
    })
    @FunctionPoint(value = "common")
    public void unCollectFeedback(@RequestParam long feedbackId) {
        feedbackService.unCollectFeedback(feedbackId);
    }

}

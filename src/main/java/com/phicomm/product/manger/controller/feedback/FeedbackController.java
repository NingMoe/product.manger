package com.phicomm.product.manger.controller.feedback;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.exception.*;
import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.feedback.*;
import com.phicomm.product.manger.model.permission.Permission;
import com.phicomm.product.manger.service.BalanceFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


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
     * 客服回复某个反馈
     *
     * @param appId      appId
     * @param sessionId  sessionId
     * @param dialogText dialogText
     * @return 是否成功
     * @throws FeedbackNotFoundException 反馈意见不存在
     * @throws DataFormatException       数据格式错误
     * @throws FeedbackEmptyException    内容为空
     * @throws FeedbackLockException     该反馈意见已经被其他人锁定
     */
    @RequestMapping(value = "feedback/customer/reply", method = RequestMethod.POST,
            consumes = "multipart/form-data", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = CommonResponse.class),
            @ApiResponse(code = 18, message = "回复为空", response = CommonResponse.class),
            @ApiResponse(code = 24, message = "反馈意见不存在", response = CommonResponse.class),
            @ApiResponse(code = 25, message = "锁定异常", response = CommonResponse.class)
    })
    @ResponseBody
    @ApiOperation("客服回复某个反馈意见")
    @FunctionPoint(value = "feedbackManger")
    public CommonResponse customerReply(@RequestParam("appIdReplay") String appId,
                                        @RequestParam("sessionIdReplay") String sessionId,
                                        @RequestParam("dialogTextReplay") String dialogText,
                                        @RequestParam("file1") MultipartFile file1,
                                        @RequestParam("file2") MultipartFile file2,
                                        @RequestParam("file3") MultipartFile file3,
                                        @RequestParam("file4") MultipartFile file4)
            throws FeedbackNotFoundException, DataFormatException, FeedbackEmptyException, FeedbackLockException {
        feedbackService.customerReply(appId, sessionId, dialogText, file1, file2, file3, file4);
        return CommonResponse.ok();
    }

    /**
     * 消息撤回
     *
     * @return 预览数据
     */
    @RequestMapping(value = "feedback/dialog/customer/revoke", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = CommonResponse.class),
            @ApiResponse(code = 21, message = "锁定异常", response = CommonResponse.class),
            @ApiResponse(code = 24, message = "会话撤回失败", response = CommonResponse.class)
    })
    @ResponseBody
    @ApiOperation("后台管理撤回用户未读的消息")
    @FunctionPoint(value = "feedbackManger")
    public CommonResponse revokeDialog(@RequestBody RevokeDialogBean revokeDialogBean)
            throws DataFormatException, DialogRevokeException, FeedbackLockException {
        feedbackService.customerRevoker(revokeDialogBean);
        return CommonResponse.ok();
    }

    /**
     * 获取反馈统计数据
     *
     * @return 反馈统计信息
     */
    @RequestMapping(value = "feedback/statistic", method = RequestMethod.POST,
            produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @ResponseBody
    @ApiOperation("获取统计数据")
    @FunctionPoint(value = "common")
    public Response<NewFeedbackCountBean> statistic() {
        NewFeedbackCountBean newFeedbackCountBean = feedbackService.feedbackStatistic();
        return new Response<NewFeedbackCountBean>().setData(newFeedbackCountBean);
    }

    /**
     * 获取反馈列表
     *
     * @param pageBean 页码
     * @return 反馈列表
     */
    @RequestMapping(value = "feedback/page/list", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = CommonResponse.class)
    })
    @ResponseBody
    @ApiOperation("后台管理获取反馈列表:页数从1开始")
    @FunctionPoint(value = "common")
    public Response<List<FeedbackWithUserInfo>> fetchByPageWithoutFilter(@RequestBody PageBean pageBean)
            throws DataFormatException {
        List<FeedbackWithUserInfo> feedbackWithUserInfoList = feedbackService.fetchFeedbackWithoutFilter(pageBean);
        return new Response<List<FeedbackWithUserInfo>>().setData(feedbackWithUserInfoList);
    }

    /**
     * 获取反馈列表
     *
     * @param historyRequestBean 要请求的数据
     * @return 反馈列表
     * @throws DataFormatException 数据格式错误
     */
    @RequestMapping(value = "feedback/page/list/filter", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = CommonResponse.class)
    })
    @ResponseBody
    @ApiOperation("后台管理获取反馈列表:页数从1开始")
    @FunctionPoint(value = "common")
    public Response<FilterWithTotalCountBean> fetchFeedbackPageList(@RequestBody HistoryRequestWithPage historyRequestBean)
            throws DataFormatException {
        FilterWithTotalCountBean feedbackWithUserInfoList = feedbackService.fetchFeedbackList(historyRequestBean);
        return new Response<FilterWithTotalCountBean>().setData(feedbackWithUserInfoList);
    }

    /**
     * 级联信息
     *
     * @return 预览数据
     */
    @RequestMapping(value = "feedback/terminal/list", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = CommonResponse.class),
            @ApiResponse(code = 25, message = "appType不存在", response = CommonResponse.class)
    })
    @ResponseBody
    @ApiOperation("后台管理获取设备终端信息")
    @FunctionPoint(value = "common")
    public Response<Map<String, List<TerminalBean>>> terminalRelation(@RequestBody AppIdBean appIdBean)
            throws DataFormatException, AppTypeNotFoundException {
        Map<String, List<TerminalBean>> result = feedbackService.obtainTerminalInfo(appIdBean);
        return new Response<Map<String, List<TerminalBean>>>().setData(result);
    }

    /**
     * 锁定反馈意见
     *
     * @param lockRequestBean 请求数据
     * @return 反馈的状态
     * @throws DataFormatException       数据格式异常
     * @throws FeedbackNotFoundException 反馈意见不存在
     */
    @RequestMapping(value = "feedback/lock", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class),
            @ApiResponse(code = 2, message = "数据格式异常", response = CommonResponse.class),
            @ApiResponse(code = 21, message = "锁定异常", response = CommonResponse.class),
            @ApiResponse(code = 22, message = "反馈意见不存在", response = CommonResponse.class)
    })
    @ResponseBody
    @ApiOperation("锁定反馈意见:客服")
    @FunctionPoint(value = "feedbackManger")
    public CommonResponse obtainFeedbackStatus(@RequestBody LockRequestBean lockRequestBean)
            throws DataFormatException, FeedbackNotFoundException, FeedbackLockException {
        feedbackService.lockFeedback(lockRequestBean);
        return CommonResponse.ok();
    }

    /**
     * 获取用户的权限列表
     *
     * @return 状态
     */
    @RequestMapping(value = "feedback/permission/list", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ApiOperation("获取用户的权限列表")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @FunctionPoint("common")
    public Response<List<Permission>> getPermissionList(){
        List<Permission> permissions = feedbackService.getPermissionList();
        return new Response<List<Permission>>().setData(permissions);
    }
}

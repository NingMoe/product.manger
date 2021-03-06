package com.phicomm.product.manger.controller.statistic;

import com.phicomm.product.manger.annotation.FunctionPoint;
import com.phicomm.product.manger.annotation.PublicInterface;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.common.Response;
import com.phicomm.product.manger.model.statistic.BalanceAccountInfo;
import com.phicomm.product.manger.model.statistic.BalanceMacStatus;
import com.phicomm.product.manger.service.BalanceStatisticService;
import com.phicomm.product.manger.service.UserAgeSectionService;
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

import java.util.Map;

/**
 * 统计数量信息
 * Created by wei.yang on 2017/6/30.
 */
@Controller
@Api(value = "统计某一信息在某个单位下的数量", description = "统计某一信息在某个单位下的数量")
public class BalanceStatisticController {

    private BalanceStatisticService balanceStatisticService;

    private UserAgeSectionService userAgeSectionService;

    @Autowired
    public BalanceStatisticController(BalanceStatisticService balanceStatisticService,
                                      UserAgeSectionService userAgeSectionService) {
        this.balanceStatisticService = balanceStatisticService;
        this.userAgeSectionService = userAgeSectionService;
        Assert.notNull(this.balanceStatisticService);
        Assert.notNull(this.userAgeSectionService);
    }

    /**
     * 新增数量统计：不包含今天
     *
     * @return 数据
     */
    @RequestMapping(value = "balance/statistic/month")
    @ResponseBody
    @ApiOperation("新增信息统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
//    @FunctionPoint("common")
    @PublicInterface
    public Response<Map<String, Integer>> obtainCountByMonth(@RequestParam int month,
                                                             @RequestParam String type) {
        Map<String, Integer> statisticData = balanceStatisticService.obtainCountByMonth(month, type);
        return new Response<Map<String, Integer>>().setData(statisticData);
    }

    /**
     * 新增数量统计：不包含今天
     *
     * @return 数据
     */
    @RequestMapping(value = "balance/statistic/day")
    @ResponseBody
    @ApiOperation("新增信息统计")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
//    @FunctionPoint("common")
    @PublicInterface
    public Response<Map<String, Integer>> obtainCountByDay(@RequestParam int day,
                                                           @RequestParam String type) {
        Map<String, Integer> statisticData = balanceStatisticService.obtainCountByDay(day, type);
        return new Response<Map<String, Integer>>().setData(statisticData);
    }

    /**
     * 查找mac相关信息
     *
     * @return 数据
     */
    @RequestMapping(value = "balance/mac/info", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("mac/sn地址信息")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @FunctionPoint("common")
    public Response<BalanceMacStatus> obtainBalanceMacInfo(@RequestParam String searchParam) throws DataFormatException {
        BalanceMacStatus balanceMacStatus = balanceStatisticService.obtainBalanceStatusInfo(searchParam);
        return new Response<BalanceMacStatus>().setData(balanceMacStatus);
    }

    /**
     * 获取电子秤mac、成员、用户等相关数量
     *
     * @return 电子秤mac、成员、用户等相关数量
     */
    @RequestMapping(value = "balance/statistic/account", method = {RequestMethod.POST, RequestMethod.GET},
            consumes = "application/json", produces = "application/json")
    @ResponseBody
    @ApiOperation("获取电子秤mac、成员、用户等相关数量")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @FunctionPoint("common")
    public Response<BalanceAccountInfo> obtainBalanceAccountInfo() {
        return new Response<BalanceAccountInfo>().setData(balanceStatisticService.obtainBalanceAccountInfo());
    }

    /**
     * 按年龄统计用户中的男女数量
     *
     * @return 不同年龄段的男女数量
     */
    @RequestMapping(value = "balance/statistic/user/analysis/age", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("按年龄统计用户中的男女数量")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @FunctionPoint("common")
    public Response<Map<String, int[]>> statisticUserByAge() {
        return new Response<Map<String, int[]>>().setData(balanceStatisticService.statisticUserByAge());
    }

    /**
     * 统计用户中的男女数量
     *
     * @return 用户中的男女数量
     */
    @RequestMapping(value = "balance/statistic/user/analysis/all", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("统计用户中的男女数量")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @FunctionPoint("common")
    public Response<Map<String, Integer>> statisticUser() {
        return new Response<Map<String, Integer>>().setData(balanceStatisticService.statisticUser());
    }

    /**
     * 按年龄统计成员中的男女数量
     *
     * @return 不同年龄段的男女数量
     */
    @RequestMapping(value = "balance/statistic/member/analysis/age", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("按年龄统计所有成员中的男女数量")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @FunctionPoint("common")
    public Response<Map<String, int[]>> statisticMemberByAge() {
        return new Response<Map<String, int[]>>().setData(balanceStatisticService.statisticMemberByAge());
    }

    /**
     * 统计成员中的男女数量
     *
     * @return 成员中的男女数量
     */
    @RequestMapping(value = "balance/statistic/member/analysis/all", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("统计所有成员中的男女数量")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = Response.class)
    })
    @FunctionPoint("common")
    public Response<Map<String, Integer>> statisticMember() {
        return new Response<Map<String, Integer>>().setData(balanceStatisticService.statisticMember());
    }

    /**
     * 获取年龄段信息
     *
     * @return 年龄段与性别之间的关系
     */
    @RequestMapping(value = "balance/statistic/user/age/v2", method = RequestMethod.POST,
            produces = "application/json", consumes = "application/json")
    @ApiOperation("获取年龄段信息")
    @ResponseBody
    @FunctionPoint("common")
    public Response<Map<String, Map>> userStatistic() {
        Map<String, Map> objectMap = userAgeSectionService.obtainUserAgeSection();
        return new Response<Map<String, Map>>().setData(objectMap);
    }

    /**
     * 获取年龄段信息
     *
     * @return 年龄段与性别之间的关系
     */
    @RequestMapping(value = "balance/statistic/member/age/v2", method = RequestMethod.POST,
            produces = "application/json", consumes = "application/json")
    @ApiOperation("获取年龄段信息")
    @ResponseBody
    @FunctionPoint("common")
    public Response<Map<String, Map>> memberStatistic() {
        Map<String, Map> objectMap = userAgeSectionService.obtainMemberAgeSection();
        return new Response<Map<String, Map>>().setData(objectMap);
    }
}



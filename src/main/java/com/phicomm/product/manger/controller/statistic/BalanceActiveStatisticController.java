package com.phicomm.product.manger.controller.statistic;

import com.phicomm.product.manger.model.common.CommonResponse;
import com.phicomm.product.manger.model.statistic.StatisticDateModel;
import com.phicomm.product.manger.service.BalanceActiveStatisticService;
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

/**
 * 统计体脂秤活跃数
 *
 * @author yufei.liu
 */
@Controller
@Api(value = "统计每日体脂秤活跃量", description = "统计每日体脂秤活跃量")
public class BalanceActiveStatisticController {

    private BalanceActiveStatisticService balanceActiveStatisticService;

    @Autowired
    public BalanceActiveStatisticController(BalanceActiveStatisticService balanceActiveStatisticService) {
        this.balanceActiveStatisticService = balanceActiveStatisticService;
        Assert.notNull(this.balanceActiveStatisticService);
    }

    /**
     * 启动定时任务
     *
     * @return 响应
     */
    @RequestMapping(value = "balance/statistic/balance/active/start/cron/task", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("启动定时任务统计每日成活跃量")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class)
    })
    public CommonResponse startCronTask() {
        balanceActiveStatisticService.cronTask();
        return CommonResponse.ok();
    }

    /**
     * 统计确定某一天的数据
     *
     * @return 响应
     */
    @RequestMapping(value = "balance/statistic/balance/active/statistic/one/day", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    @ApiOperation("统计某一天的体脂秤活跃量")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "正常情况", response = CommonResponse.class)
    })
    public CommonResponse statisticOneDay(@RequestBody StatisticDateModel statisticDateModel) {
        balanceActiveStatisticService.statisticOneDay(statisticDateModel);
        return CommonResponse.ok();
    }

}

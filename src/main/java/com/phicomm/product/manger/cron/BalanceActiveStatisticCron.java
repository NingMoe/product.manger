package com.phicomm.product.manger.cron;

import com.phicomm.product.manger.service.BalanceActiveStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 每日统计体脂秤活跃量的定时任务
 *
 * @author yufei.liu
 */
@Component
public class BalanceActiveStatisticCron {

    private BalanceActiveStatisticService balanceActiveStatisticService;

    @Autowired
    public BalanceActiveStatisticCron(BalanceActiveStatisticService balanceActiveStatisticService) {
        this.balanceActiveStatisticService = balanceActiveStatisticService;
        Assert.notNull(this.balanceActiveStatisticService);
    }

    /**
     * 每天晚上3点执行一次
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void statistic() {
        balanceActiveStatisticService.cronTask();
    }

}

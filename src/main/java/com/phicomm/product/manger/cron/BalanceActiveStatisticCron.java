package com.phicomm.product.manger.cron;

import com.phicomm.product.manger.service.BalanceActiveStatisticService;
import com.phicomm.product.manger.service.BalanceMeasureSourceService;
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

    private BalanceMeasureSourceService balanceMeasureSourceService;

    @Autowired
    public BalanceActiveStatisticCron(BalanceActiveStatisticService balanceActiveStatisticService,
                                      BalanceMeasureSourceService balanceMeasureSourceService) {
        this.balanceActiveStatisticService = balanceActiveStatisticService;
        this.balanceMeasureSourceService = balanceMeasureSourceService;
        Assert.notNull(this.balanceActiveStatisticService);
        Assert.notNull(this.balanceMeasureSourceService);
    }


    /**
     * 每天早上3点执行一次
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void statistic() {
        balanceActiveStatisticService.cronTask();
    }

    /**
     * 每天早上3半点执行一次
     */
    @Scheduled(cron = "0 30 3 * * ?")
    public void dataSource(){
        balanceMeasureSourceService.updateSourceStatistic();
    }

}

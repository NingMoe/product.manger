package com.phicomm.product.manger.cron;

import com.phicomm.product.manger.service.BalanceDailyStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *  统计体脂称24小时分布数据，以及电极数数据
 * Created by song02.cao on 2017/11/22.
 */

@Component
public class Balance24HourAndElectrodeStatisticCron {

    private BalanceDailyStatisticService balanceDailyStatisticService;

    @Autowired
    public Balance24HourAndElectrodeStatisticCron(BalanceDailyStatisticService balanceDailyStatisticService) {
        this.balanceDailyStatisticService = balanceDailyStatisticService;
        Assert.notNull(this.balanceDailyStatisticService);
    }

    /**
     * 每天晚上2点执行一次
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void statistic() {
        balanceDailyStatisticService.cronTask();
        System.out.println("caosong scheduled working now~~~~~");
    }
}

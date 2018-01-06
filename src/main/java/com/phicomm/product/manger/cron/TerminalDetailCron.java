package com.phicomm.product.manger.cron;

import com.phicomm.product.manger.service.TerminalStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 同步设备统计信息，不考虑放到缓存
 *
 * @author wei.yang on 2017/12/29
 */
@Component
public class TerminalDetailCron {

    private TerminalStatisticService terminalStatisticService;

    @Autowired
    public TerminalDetailCron(TerminalStatisticService terminalStatisticService) {
        this.terminalStatisticService = terminalStatisticService;
    }

    /**
     * 每次全部同步开销有点大，但按道理来说我只需要同步昨天的数据到数据库就行
     * 1点的时候同步数据
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void syncTerminalInfo() throws InterruptedException {
        terminalStatisticService.syncYesterdayData();
        Thread.sleep(5 * 60 * 1000);
    }
}

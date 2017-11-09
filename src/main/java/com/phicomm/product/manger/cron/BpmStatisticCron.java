package com.phicomm.product.manger.cron;

import com.phicomm.product.manger.service.BpmMeasureStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * Created by yafei.hou on 2017/11/8.
 */
@Component
public class BpmStatisticCron {

    private BpmMeasureStatisticService bpmMeasureStatisticService;

    @Autowired
    public BpmStatisticCron(BpmMeasureStatisticService bpmMeasureStatisticService) {
        this.bpmMeasureStatisticService = bpmMeasureStatisticService;
    }

    /**
     * 每天两点更新数据表
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void startCron(){
        bpmMeasureStatisticService.cronTask();
    }
}

package com.phicomm.product.manger.cron;

import com.phicomm.product.manger.service.BpmMeasureStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *
 * Created by yafei.hou on 2017/11/8.
 * @author yafei.hou
 */
@Component
public class BpmStatisticCron {

    private BpmMeasureStatisticService bpmMeasureStatisticService;

    @Autowired
    public BpmStatisticCron(BpmMeasureStatisticService bpmMeasureStatisticService) {
        this.bpmMeasureStatisticService = bpmMeasureStatisticService;
        Assert.notNull(this.bpmMeasureStatisticService);
    }

    /**
     * 每天两点更新数据表
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void startCron(){
        bpmMeasureStatisticService.cronTask();
    }
}

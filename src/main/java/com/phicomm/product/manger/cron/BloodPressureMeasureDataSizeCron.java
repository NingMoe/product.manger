package com.phicomm.product.manger.cron;

import com.phicomm.product.manger.service.BloodPressureMeasureDataSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * Created by yafei.hou on 2017/11/8.
 */
@Component
public class BloodPressureMeasureDataSizeCron {

    private BloodPressureMeasureDataSizeService bloodPressureMeasureDataSizeService;

    @Autowired
    public BloodPressureMeasureDataSizeCron(BloodPressureMeasureDataSizeService bloodPressureMeasureDataSizeService) {
        this.bloodPressureMeasureDataSizeService = bloodPressureMeasureDataSizeService;
    }

    /**
     * 每天两点更新数据表
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void startCron(){
        bloodPressureMeasureDataSizeService.cronTask();
    }
}

package com.phicomm.product.manger.cron;

import com.phicomm.product.manger.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户活跃度定时任务
 *
 * @author qiang.ren
 * @date 2018/1/10
 */
@Component
public class UserActivityCron {

    private UserActivityService userActivityService;

    @Autowired
    public UserActivityCron(UserActivityService userActivityService){
        this.userActivityService = userActivityService;
        Assert.notNull(this.userActivityService);
    }

    /**
     * 半小时同步一次
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void syncUserActivityInfo() {
        userActivityService.syncUserActivityInfo(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
}

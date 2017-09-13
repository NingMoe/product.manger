package com.phicomm.product.manger.cron;

import com.phicomm.product.manger.service.UserAgeSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 自动更新
 * Created by wei.yang on 2017/9/5.
 */
@Component
public class UserAgeSectionCron {

    private UserAgeSectionService userAgeSectionService;

    @Autowired
    public UserAgeSectionCron(UserAgeSectionService userAgeSectionService) {
        this.userAgeSectionService = userAgeSectionService;
    }

    /**
     * 更新数据库内容:12小时更新一次
     */
    @Scheduled(cron = "0 0 */12 * * ?")
    public void update() {
        userAgeSectionService.update();
    }
}

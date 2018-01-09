package com.phicomm.product.manger.cron;

import com.phicomm.product.manger.service.UserAgeSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 自动更新
 *
 * @author wei.yang on 2017/9/5.
 */
@Component
public class UserAgeSectionCron {

    private UserAgeSectionService userAgeSectionService;

    @Autowired
    public UserAgeSectionCron(UserAgeSectionService userAgeSectionService) {
        this.userAgeSectionService = userAgeSectionService;
        Assert.notNull(this.userAgeSectionService);
    }

    /**
     * 更新数据库内容:12小时更新一次
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void update() {
        userAgeSectionService.update();
    }
}

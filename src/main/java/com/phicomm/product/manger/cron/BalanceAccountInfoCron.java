package com.phicomm.product.manger.cron;

import com.phicomm.product.manger.dao.BalanceCronStatisticMapper;
import com.phicomm.product.manger.dao.BalanceStatusMapper;
import com.phicomm.product.manger.model.statistic.BalanceAccountInfo;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 体脂秤定时获取账号信息
 *
 * @author song02.cao
 */

@Component
public class BalanceAccountInfoCron {

    private static final Logger logger = Logger.getLogger(BalanceAccountInfoCron.class);

    private BalanceStatusMapper balanceStatusMapper;

    private BalanceCronStatisticMapper balanceCronStatisticMapper;

    @Autowired
    public BalanceAccountInfoCron(BalanceStatusMapper balanceStatusMapper, BalanceCronStatisticMapper balanceCronStatisticMapper) {
        this.balanceStatusMapper = balanceStatusMapper;
        this.balanceCronStatisticMapper = balanceCronStatisticMapper;
        Assert.notNull(this.balanceStatusMapper, "balanceStatusMapper is null");
        Assert.notNull(this.balanceCronStatisticMapper, "balanceCronStatisticMapper is null");
    }

    /**
     * 每天1小时执行一次,将体脂秤账号信息写入到
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void statistic() {
        logger.info("定时任务：每小时更新体脂秤账号统计信息running now");
        CustomerContextHolder.selectProdDataSource();
        BalanceAccountInfo balanceAccountInfo = balanceStatusMapper.obtainAccountInfo();
        CustomerContextHolder.clearDataSource();
        CustomerContextHolder.selectLocalDataSource();
        balanceCronStatisticMapper.insertUpateBalanceAccountInfo(
                balanceAccountInfo.getUserCount(),
                balanceAccountInfo.getMemberCount(),
                balanceAccountInfo.getMacCount()
        );
        CustomerContextHolder.clearDataSource();
    }
}

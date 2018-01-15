package com.phicomm.product.manger.cron;

import com.phicomm.product.manger.dao.*;
import com.phicomm.product.manger.model.statistic.BalanceAccountInfo;
import com.phicomm.product.manger.model.statistic.LocationCountBean;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 体脂秤定时获取账号信息
 *
 * @author song02.cao
 */

@Component
public class BalanceStatisticCron {

    private static final Logger logger = Logger.getLogger(BalanceStatisticCron.class);

    private BalanceStatusMapper balanceStatusMapper;

    private BalanceCronStatisticMapper balanceCronStatisticMapper;

    private UserInfoMapper userInfoMapper;

    private BalanceUserManagerMapper balanceUserManagerMapper;

    private BalanceLocationMapper balanceLocationMapper;

    @Autowired
    public BalanceStatisticCron(BalanceStatusMapper balanceStatusMapper,
                                BalanceCronStatisticMapper balanceCronStatisticMapper,
                                UserInfoMapper userInfoMapper,
                                BalanceUserManagerMapper balanceUserManagerMapper,
                                BalanceLocationMapper balanceLocationMapper) {
        this.balanceStatusMapper = balanceStatusMapper;
        this.balanceCronStatisticMapper = balanceCronStatisticMapper;
        this.userInfoMapper = userInfoMapper;
        this.balanceUserManagerMapper = balanceUserManagerMapper;
        this.balanceLocationMapper = balanceLocationMapper;
        Assert.notNull(this.balanceStatusMapper, "balanceStatusMapper is null");
        Assert.notNull(this.balanceCronStatisticMapper, "balanceCronStatisticMapper is null");
        Assert.notNull(this.userInfoMapper, "userInfoMapper is null");
        Assert.notNull(this.balanceUserManagerMapper, "balanceUserManagerMapper is null");
        Assert.notNull(this.balanceLocationMapper, "balanceLocationMapper is null");
    }

    /**
     * 每天1小时执行一次,将体脂秤账号信息写入到
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void accountStatistic() {
        logger.info("定时任务：每小时整更新 '体脂秤账号' 统计信息running now");
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

    /**
     * 定时任务，每小时执行一次，将用户性别统计信息写入到表中
     */
    @Scheduled(cron = "0 5 * * * ?")
    public void userGenderCountStatistic() {
        logger.info("定时任务:每小时05分更新 '体脂称用户性别' 统计信息running now");

        CustomerContextHolder.selectProdDataSource();
        int menNum = userInfoMapper.statisticUser(1);
        int womenNum = userInfoMapper.statisticUser(0);
        CustomerContextHolder.clearDataSource();

        CustomerContextHolder.selectLocalDataSource();
        balanceCronStatisticMapper.insertUpdateBalanceUserCountInfo(0, "男", menNum);
        balanceCronStatisticMapper.insertUpdateBalanceUserCountInfo(1, "女", womenNum);
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 定时任务，每小时执行一次，将用户成员性别统计信息写入到表中
     */
    @Scheduled(cron = "0 6 * * * ?")
    public void memberGenderCountStatistic() {
        logger.info("定时任务:每小时07分更新 '体脂称用户成员性别' 统计信息running now");

        CustomerContextHolder.selectProdDataSource();
        int menNum = balanceUserManagerMapper.statisticMember(1);
        int womenNum = balanceUserManagerMapper.statisticMember(0);
        CustomerContextHolder.clearDataSource();

        CustomerContextHolder.selectLocalDataSource();
        balanceCronStatisticMapper.insertUpdateBalanceMemberCountInfo(0, "男", menNum);
        balanceCronStatisticMapper.insertUpdateBalanceMemberCountInfo(1, "女", womenNum);
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 定时任务，每小时执行一次，体脂秤销售地区统计
     */
    @Scheduled(cron = "0 6 * * * ?")
    public void saleLocationCountStatistic() {
        logger.info("定时任务:每小时06分更新 '体脂秤销售地区' 统计信息running now");

        /*取30天的数据*/
        int day = 30;
        /*取12个月的数据*/
        int month = 12;
        /*取前15个省份*/
        int pageSize = 15;
        CustomerContextHolder.selectProdDataSource();
        List<LocationCountBean> daysCountBeans = balanceLocationMapper.obtainLocationCountByDay(day, pageSize);
        List<LocationCountBean> monthsCountBeans = balanceLocationMapper.obtainLocationCountByMonth(month, pageSize);
        CustomerContextHolder.clearDataSource();

        CustomerContextHolder.selectLocalDataSource();
        balanceCronStatisticMapper.cleanBalanceSaleLocationCountInfo();
        balanceCronStatisticMapper.insertBalanceSaleLocationCountInfo(daysCountBeans);

        balanceCronStatisticMapper.cleanBalanceSaleLocation12MonthsCountInfo();
        balanceCronStatisticMapper.insertBalanceSaleLocation12MonthsCountInfo(monthsCountBeans);
        CustomerContextHolder.clearDataSource();
    }
}

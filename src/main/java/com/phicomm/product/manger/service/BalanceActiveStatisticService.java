package com.phicomm.product.manger.service;

import com.phicomm.product.manger.dao.BalanceActiveStatisticMapper;
import com.phicomm.product.manger.model.statistic.StatisticDateModel;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 体脂秤激活数量统计统计业务
 *
 * @author yufei.liu
 */
@Service
public class BalanceActiveStatisticService {

    private static final Logger logger = Logger.getLogger(BalanceActiveStatisticService.class);

    private static final int BALANCE_MAC_MEASURE_SPLIT_TABLE = 5;

    private BalanceActiveStatisticMapper balanceActiveStatisticMapper;

    @Autowired
    public BalanceActiveStatisticService(BalanceActiveStatisticMapper balanceActiveStatisticMapper) {
        this.balanceActiveStatisticMapper = balanceActiveStatisticMapper;
        Assert.notNull(this.balanceActiveStatisticMapper);
    }

    /**
     * 每天进行一次日活统计
     */
    public void cronTask() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date endTime = calendar.getTime();
        LocalDateTime localDateTime = new LocalDateTime(endTime);
        Date startTime = localDateTime.minusDays(1).toDate();
        long sum = statistic(startTime, endTime);
        CustomerContextHolder.selectLocalDataSource();
        balanceActiveStatisticMapper.insert(startTime, sum);
        CustomerContextHolder.clearDataSource();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info(String.format("startTime = %s, endTime = %s, balance active count is %s.",
                simpleDateFormat.format(startTime), simpleDateFormat.format(endTime), sum));
    }

    /**
     * 在范围内进行统计活跃的体脂秤数量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public long statistic(Date startTime, Date endTime) {
        long sum = 0;
        CustomerContextHolder.selectProdDataSource();
        try {
            for (int i = 0; i < BALANCE_MAC_MEASURE_SPLIT_TABLE; i++) {
                sum += balanceActiveStatisticMapper.statistic(startTime, endTime, i);
            }
        } finally {
            CustomerContextHolder.clearDataSource();
        }
        return sum;
    }

    /**
     * 统计确定某一天的数据
     *
     * @param statisticDateModel 统计某一天的数据
     */
    public void statisticOneDay(StatisticDateModel statisticDateModel) {
        Date startDate = statisticDateModel.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startTime = calendar.getTime();
        LocalDateTime localDateTime = new LocalDateTime(startTime);
        Date endTime = localDateTime.plusDays(1).toDate();
        long sum = statistic(startTime, endTime);
        CustomerContextHolder.selectLocalDataSource();
        balanceActiveStatisticMapper.insert(startTime, sum);
        CustomerContextHolder.clearDataSource();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info(String.format("startTime = %s, endTime = %s, balance active count is %s.",
                simpleDateFormat.format(startTime), simpleDateFormat.format(endTime), sum));
    }

}

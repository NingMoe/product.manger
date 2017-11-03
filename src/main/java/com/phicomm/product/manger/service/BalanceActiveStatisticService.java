package com.phicomm.product.manger.service;

import com.phicomm.product.manger.dao.BalanceActiveStatisticMapper;
import com.phicomm.product.manger.model.statistic.BalanceActiveQueryModel;
import com.phicomm.product.manger.model.statistic.BalanceActiveQueryResultModel;
import com.phicomm.product.manger.model.statistic.BalanceActiveStatisticModel;
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
import java.util.List;

/**
 * 体脂秤激活数量统计统计业务
 *
 * @author yufei.liu
 */
@Service
public class BalanceActiveStatisticService {

    private static final Logger logger = Logger.getLogger(BalanceActiveStatisticService.class);

    private static final int BALANCE_MAC_MEASURE_SPLIT_TABLE = 5;

    private static final int SHOW_BALANCE_MEASURE_DATE_NUMBER = 20;

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
        BalanceActiveStatisticModel sum = statistic(startTime, endTime);
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
    public BalanceActiveStatisticModel statistic(Date startTime, Date endTime) {
        long sumForPv = 0;
        long sumForUv = 0;
        CustomerContextHolder.selectProdDataSource();
        try {
            for (int i = 0; i < BALANCE_MAC_MEASURE_SPLIT_TABLE; i++) {
                sumForPv += balanceActiveStatisticMapper.statisticForPv(startTime, endTime, i);
                sumForUv += balanceActiveStatisticMapper.statisticForUv(startTime, endTime, i);
            }
        } finally {
            CustomerContextHolder.clearDataSource();
        }
        return new BalanceActiveStatisticModel(sumForPv, sumForUv);
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
        BalanceActiveStatisticModel sum = statistic(startTime, endTime);
        CustomerContextHolder.selectLocalDataSource();
        balanceActiveStatisticMapper.insert(startTime, sum);
        CustomerContextHolder.clearDataSource();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info(String.format("startTime = %s, endTime = %s, balance active count is %s.",
                simpleDateFormat.format(startTime), simpleDateFormat.format(endTime), sum));
    }

    /**
     * 统计几天的数据
     *
     * @param statisticDateModels 日期
     */
    public void statisticSomeDay(List<StatisticDateModel> statisticDateModels) {
        statisticDateModels.forEach(this::statisticOneDay);
    }

    /**
     * 获得画图的数据
     *
     * @return 统计结果集
     */
    public BalanceActiveQueryResultModel getDrawChartData() {
        List<BalanceActiveQueryModel> balanceActiveQueryModelList = balanceActiveStatisticMapper.getDrawChartData(SHOW_BALANCE_MEASURE_DATE_NUMBER);
        int size = balanceActiveQueryModelList.size();
        String[] dates = new String[size];
        int[] pvs = new int[size];
        int[] uvs = new int[size];
        for (int i = size - 1; i >= 0; i--) {
            dates[i] = balanceActiveQueryModelList.get(i).getDate();
            pvs[i] = balanceActiveQueryModelList.get(i).getActiveCountPv();
            uvs[i] = balanceActiveQueryModelList.get(i).getActiveCountUv();
        }
        BalanceActiveQueryResultModel balanceActiveQueryResultModel = new BalanceActiveQueryResultModel();
        balanceActiveQueryResultModel.setDates(dates);
        balanceActiveQueryResultModel.setPvs(pvs);
        balanceActiveQueryResultModel.setUvs(uvs);
        return balanceActiveQueryResultModel;
    }

}

package com.phicomm.product.manger.service;

import com.google.common.collect.HashMultimap;
import com.phicomm.product.manger.dao.BalanceDailyStatisticMapper;
import com.phicomm.product.manger.model.statistic.*;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import io.swagger.models.auth.In;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.joda.time.LocalDateTime;

import java.util.*;


/**
 * Created by song02.cao on 2017/11/16.
 */

@Service
public class BalanceDailyStatisticService {

    private static final Logger logger = Logger.getLogger(BalanceDailyStatisticService.class);

    private static final int BALANCE_MEASURE_TABLE_NUM = 50;

    private static final int HOURS_DISPLAY = 24;

    private static final int ELECTRODE_0 = 0;

    private static final int ELECTRODE_4 = 4;

    private static final int ELECTRODE_8 = 8;

    private static final int DAY_RANGE = 20;

    private BalanceDailyStatisticMapper balanceDailyStatisticMapper;

    @Autowired
    public BalanceDailyStatisticService(BalanceDailyStatisticMapper balanceDailyStatisticMapper) {
        this.balanceDailyStatisticMapper = balanceDailyStatisticMapper;
        Assert.notNull(this.balanceDailyStatisticMapper);
    }

    /**
     * 将表balance_measure_info中的24小时统计信息写入到表balance_measure_24_hour_count中
     */
    public void setBalance24HourCount() {
        int[] counts = new int[HOURS_DISPLAY];
        for (int i = 0; i < HOURS_DISPLAY; i++) {
            counts[i] = 0;
        }
        for (int i = 1; i < BALANCE_MEASURE_TABLE_NUM; i++) {
            List<BalanceAsHourModel> hourStatisticList = new ArrayList<>();
            CustomerContextHolder.selectProdDataSource();
            hourStatisticList.addAll(balanceDailyStatisticMapper.getBalanceStatistic24HourDisplay(i));
            CustomerContextHolder.clearDataSource();
            for (BalanceAsHourModel balanceAsHourModel : hourStatisticList) {
                int index = Integer.parseInt(balanceAsHourModel.getTimeAsHour());
                counts[index] = counts[index] + balanceAsHourModel.getCount();
            }
        }
        for (int i = 0; i < HOURS_DISPLAY; i++) {
            CustomerContextHolder.selectLocalDataSource();
            balanceDailyStatisticMapper.setBalance24HourCount(counts[i], String.valueOf(i));
            CustomerContextHolder.clearDataSource();
        }
    }

    /**
     * 将表balance_measure_24_hour_count中的数据取出，用于页面图标的显示
     *
     * @return Balance24HourDispalyQueryResultModel 返回页面的数据格式{String[]hour, int[]count}
     */
    public Balance24HourDisplayQueryResultModel getBalance24HourCount() {
        List<BalanceAsHourModel> hourModelList = new ArrayList<>();
        Balance24HourDisplayQueryResultModel resultModel = new Balance24HourDisplayQueryResultModel();
        CustomerContextHolder.selectLocalDataSource();
        hourModelList = balanceDailyStatisticMapper.getBalance24HourCount();
        CustomerContextHolder.clearDataSource();
        int size = hourModelList.size();
        String[] hours = new String[size];
        int[] counts = new int[size];
        for (int i = 0; i < size; i++) {
            hours[i] = hourModelList.get(i).getTimeAsHour();
            counts[i] = hourModelList.get(i).getCount();
        }
        resultModel.setHours(hours);
        resultModel.setCounts(counts);
        return resultModel;
    }


    /**
     *  将balance_measure_24_hour_count中的数据全部删除
     *
     */
    public void deleteBalance24HourCount() {
        balanceDailyStatisticMapper.deleteBalance24HourCount();
    }

    /**
     * 将一天的体脂称电极数据写入到表balance_measure_electrode_statistic_each_day中
     *
     * @param date 日期
     */
    public void setBalanceElectrodeStatisticOneDay(String date) {
        List<BalanceElectrodeModel> list = new ArrayList<>();
        List<BalanceElectrodeModel> resultStandStructList = new ArrayList<>();
        int electrode0 = 0;
        int electrode4 = 0;
        int electrode8 = 0;
        //@todo tablbe index从多少算起？
        for (int i = 1; i < BALANCE_MEASURE_TABLE_NUM; i++) {
            CustomerContextHolder.selectProdDataSource();
            list.addAll(balanceDailyStatisticMapper.getBalanceElectrodeInfoOnedayFromOriginal(stringToDate(date), i));
            CustomerContextHolder.clearDataSource();
        }
        for (BalanceElectrodeModel item : list) {
            switch (item.getElectrodeNumber()) {
                case 0:
                    electrode0 += item.getCounts();
                    break;
                case 4:
                    electrode4 += item.getCounts();
                    break;
                case 8:
                    electrode8 += item.getCounts();
                    break;
            }
        }
        CustomerContextHolder.selectLocalDataSource();
        balanceDailyStatisticMapper.setBalanceElectrodeStatistic(stringToDate(date), electrode0, electrode4, electrode8);
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 获取最近20天的电极数据
     *
     * @param date 当前日期
     * @return BalanceElectrodeQueryResultModel     返回{String[]dates,int[]electrode0, int[]electrode4, int[]electrode8}对象
     */
    public BalanceElectrodeQueryResultModel get20DaysBalanceElectrodeStatistic(Date date) {
        BalanceElectrodeQueryResultModel balanceElectrodeQueryResultModel = new BalanceElectrodeQueryResultModel();
        List<BalanceElectrodeOutModel> list = balanceDailyStatisticMapper.getBalanceElectrodeInfoDayFrame(date, 20);
        int size = list.size() <= DAY_RANGE ? list.size() : DAY_RANGE;
        String[] dates = new String[size];
        int[] electrode0Counts = new int[size];
        int[] electrode4Counts = new int[size];
        int[] electrode8Counts = new int[size];
        for (int i = 0; i < size; i++) {
            dates[i] = list.get(i).getDate();
            electrode0Counts[i] = list.get(i).getElectrode0Count();
            electrode4Counts[i] = list.get(i).getElectrode4Count();
            electrode8Counts[i] = list.get(i).getElectrode8Count();
        }
        balanceElectrodeQueryResultModel.setDates(dates);
        balanceElectrodeQueryResultModel.setElectrode0Counts(electrode0Counts);
        balanceElectrodeQueryResultModel.setElectrode4Counts(electrode4Counts);
        balanceElectrodeQueryResultModel.setElectrode8Counts(electrode8Counts);
        return balanceElectrodeQueryResultModel;
    }

    /**
     *  将所有balance_measure_info 表中的数据写入到表balance_measure_electrode_statistic_each_day中
     *
     *  @todo 该方法效率太低，后续优化
     */
    public void setBalanceElectrodeStatisticFromAllHistory() {
        String maxDate = "1999-01-01";
        String minDate = "9999-01-01";
        for (int i = 1; i < BALANCE_MEASURE_TABLE_NUM; i++) {
            CustomerContextHolder.selectProdDataSource();
            BalanceMaxMinDatesModel balanceMaxMinDatesModel = balanceDailyStatisticMapper.getBalanceMaxMinDates(i);
            CustomerContextHolder.clearDataSource();
            maxDate = maxDate.compareTo(balanceMaxMinDatesModel.getMaxDate()) > 0 ? maxDate : balanceMaxMinDatesModel.getMaxDate();
            minDate = minDate.compareTo(balanceMaxMinDatesModel.getMinDate()) < 0 ? minDate : balanceMaxMinDatesModel.getMinDate();
        }
        setAllElectrodeIntoTable(minDate, maxDate);
    }

    /**
     * 每天进行一次日活统计
     *
     */
    public void cronTask() {
        Date date = new Date();
        String yesterday =  getYesterday(date);
        deleteBalance24HourCount();
        setBalance24HourCount();
        setBalanceElectrodeStatisticOneDay(yesterday);
    }

    /**
     * 将list转换成hashMultimap
     *
     * @param list 需要转换的list
     * @return HashMultimap     HashMultimap结构体
     */
    private HashMultimap<String, Integer> list2HashMultiMap(List<BalanceAsHourModel> list) {
        Assert.notNull(list);
        HashMultimap<String, Integer> map = HashMultimap.create();
        for (BalanceAsHourModel balanceAsHourModel : list) {
            map.put(balanceAsHourModel.getTimeAsHour(), balanceAsHourModel.getCount());
        }
        return map;
    }


    /**
     *   将Date类型转换成String类型日期
     *
     * @param date      Date类型日期
     * @return String   转换后的日期字符串
     */
    @NotNull
    private String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     *  将String类型的日期转换成Date类型
     *
     * @param date   String类型日期
     * @return Date   Date类型日期
     */
    @Nullable
    private Date stringToDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将balance_measure_info_<x>表中所有的测量电极信息写入到表balance_measure_electrode_statistic_each_day中
     *
     * @param startDateString 开始日期
     * @param endDateString   结束日期
     */
    private void setAllElectrodeIntoTable(String startDateString, String endDateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = stringToDate(startDateString);
        Date endDate = stringToDate(endDateString);
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        while (start.compareTo(end) <= 0) {
            String date = simpleDateFormat.format(start.getTime());
            setBalanceElectrodeStatisticOneDay(date);
            start.add(Calendar.DATE, 1);
        }
    }

    /**
     *  根据当前日期，获取昨天的日期
     *
     * @param date      当前日期
     * @return string    昨天日期
     */
    public String getYesterday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        Date yestoday = calendar.getTime();
        return dateToString(yestoday);
    }
}
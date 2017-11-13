package com.phicomm.product.manger.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.phicomm.product.manger.dao.BpmBindStatisticMapper;
import com.phicomm.product.manger.model.bpm.BpmCountBean;
import com.phicomm.product.manger.model.bpm.BpmMeasureBean;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 血压测量数据 相关统计
 * Created by yafei.hou on 2017/11/6.
 *
 * @author yafei.hou
 */
@Service
public class BpmStatisticService {

    private static final Logger logger = Logger.getLogger(BpmBindStatisticMapper.class);

    private static final String MONTH_KEY = "month";
    private static final String TODAY_KEY = "today";
    private static final int HOURS = 24;
    private static final int TABLES = 20;
    private static final String DAY_PATTERN_MM_DD = "MM-dd";
    private static final String MONTH_PATTERN_YY_MM = "yy-MM";
    private static final String HOUR_PATTERN_HH = "HH";
    private BpmBindStatisticMapper bpmBindStatisticMapper;


    @Autowired
    public BpmStatisticService(BpmBindStatisticMapper bpmBindStatisticMapper) {
        this.bpmBindStatisticMapper = bpmBindStatisticMapper;
        Assert.notNull(this.bpmBindStatisticMapper);
    }


    /**
     * 获取绑定的血压计数量  最近N天的数据
     *
     * @param day 最近N天的数据
     * @return 返回key：日期，value：数量；的映射表
     */
    public Map<String, Integer> obtainBPMCountByDay(int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DAY_PATTERN_MM_DD);
        List<BpmCountBean> countBeans;
        CustomerContextHolder.selectProdDataSource();
        countBeans = bpmBindStatisticMapper.obtainBpmCountByDay(day);
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>(0);
        }
        Map<String, Integer> result = new TreeMap<>();
        for (BpmCountBean countBean : countBeans) {
            result.put(countBean.getBindPBMTime(), countBean.getBindPBMCount());
        }
        addZero(day, dateFormat, result);
        return result;
    }


    /**
     * 最近N月绑定数量统计
     *
     * @param months 最近N天的数据
     * @return 返回key：日期，value：数量；的映射表
     */
    public Map<String, Integer> obtainBpmCountByMonth(int months) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(MONTH_PATTERN_YY_MM);
        List<BpmCountBean> countBeans;
        CustomerContextHolder.selectProdDataSource();
        countBeans = bpmBindStatisticMapper.obtainBpmCountByMonth(months);
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>(0);
        }
        Map<String, Integer> result = new TreeMap<>();
        for (BpmCountBean countBean : countBeans) {
            result.put(countBean.getBindPBMTime(), countBean.getBindPBMCount());
        }
        addZeroByMonth(months, dateFormat, result);
        return result;
    }


    /**
     * 绑定的血压计总数
     *
     * @return int：数量
     */
    public int obtainBpmCountBindAll() {
        CustomerContextHolder.selectProdDataSource();
        int result = bpmBindStatisticMapper.obtainBpmCountAll();
        CustomerContextHolder.clearDataSource();
        return result;
    }


    /**
     * 获取血压计测量的数据的条数，最近N天，每天多少条
     *
     * @param day 最近N天的数据
     * @return 返回key：日期，value：数量；的映射表
     */
    public Map<String, Integer> obtainBPMeasureCountByDay(int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DAY_PATTERN_MM_DD);
        List<BpmMeasureBean> countBeans = Lists.newArrayList();
        CustomerContextHolder.selectProdDataSource();
        for (int i = 0; i < TABLES; i++) {
            countBeans.addAll(bpmBindStatisticMapper.obtainBpmMeasureByDay(i, day));
        }
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>(0);
        }
        Map<String, Integer> result = new TreeMap<>();
        list2map(countBeans, result);
        addZero(day, dateFormat, result);
        logger.info("day--\n" + result);
        return result;
    }

    /**
     * 对于日期为key的map， 如果某个日期没有值，则在该日期下添加value=0
     *
     * @param day    天数
     * @param result void 传入的是map的引用，因此该方法可以对map进行修改。
     */
    private void addZero(int day, SimpleDateFormat dateFormat, Map<String, Integer> result) {
        DateTime dateTime = new DateTime(DateTime.now()).minusDays(1);
        for (int i = day; i > 0; i--) {
            String bindPBMTime = dateFormat.format(dateTime.toDate());
            if (!result.containsKey(bindPBMTime)) {
                result.put(bindPBMTime, 0);
            }
            dateTime = dateTime.minusDays(1);
        }
    }

    /**
     * 对于日期为key的map， 如果某个日期没有值，则在该日期下添加value=0
     */
    private void addZeroByMonth(int months, SimpleDateFormat dateFormat, Map<String, Integer> result) {
        DateTime dateTime = new DateTime(DateTime.now());
        for (int i = months; i > 0; i--) {
            String bindPBMTime = dateFormat.format(dateTime.toDate());
            if (!result.containsKey(bindPBMTime)) {
                result.put(bindPBMTime, 0);
            }
            dateTime = dateTime.minusMonths(1);
        }
    }

    /**
     * 获取血压计测量的数据的条数，最近N月，每月多少条
     *
     * @param month 最近N月的数据
     * @return 返回key：日期，value：数量；的映射表
     */
    public Map<String, Integer> obtainBpmMeasureByMonth(int month) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(MONTH_PATTERN_YY_MM);
        List<BpmMeasureBean> countBeans = Lists.newArrayList();
        CustomerContextHolder.selectProdDataSource();
        for (int i = 0; i < TABLES; i++) {
            countBeans.addAll(bpmBindStatisticMapper.obtainBpmMeasureByMonth(i, month));
        }
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>(0);
        }
        Map<String, Integer> result = new TreeMap<>();
        list2map(countBeans, result);
        addZeroByMonth(month, dateFormat, result);
        return result;
    }

    /**
     * 获取血压计测量的数据的条数，24小时的时间分布
     *
     * @return 返回key：日期，value：数量；的映射表
     */
    public Map<String, Integer> obtainBpmMeasureByHour() {
        DateTime dateTime = new DateTime(DateTime.now()).minusDays(1);
        SimpleDateFormat dateFormat = new SimpleDateFormat(HOUR_PATTERN_HH);
        List<BpmMeasureBean> countBeans = Lists.newArrayList();
        CustomerContextHolder.selectProdDataSource();
        for (int i = 0; i < TABLES; i++) {
            countBeans.addAll(bpmBindStatisticMapper.obtainBpmMeasureTime(i));
        }
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>(0);
        }
        Map<String, Integer> result = new TreeMap<>();
        list2map(countBeans, result);
        for (int i = HOURS; i > 0; i--) {
            String bindPBMTime = dateFormat.format(dateTime.toDate());
            if (!result.containsKey(bindPBMTime)) {
                result.put(bindPBMTime, 0);
            }
            dateTime = dateTime.minusHours(1);
        }
        logger.info("hour_data:"+result);
        return result;
    }

    /**
     * 将list中的对象转换为map元素
     *
     * @param countBeans List<BpmMeasureBean>
     * @param result     Map<String, Integer>
     */
    private void list2map(List<BpmMeasureBean> countBeans, Map<String, Integer> result) {
        for (BpmMeasureBean countBean : countBeans) {
            String key = countBean.getMeasureTime();
            if (!result.containsKey(key)) {
                result.put(key, countBean.getMeasureCount());
            } else {
                result.put(key, result.get(key) + countBean.getMeasureCount());
            }
        }
    }

    /**
     * 总计测量数据数量
     *
     * @return int 总计测量数据数量
     */
    public int obtainBpmMeasureCounts() {
        int sum = 0;
        CustomerContextHolder.selectProdDataSource();
        for (int i = 0; i < TABLES; i++) {
            sum += bpmBindStatisticMapper.obtainBpmMeasureCounts(i);
        }
        logger.info(sum);
        CustomerContextHolder.clearDataSource();
        return sum;
    }

    /**
     * 获取当月和当天用户测量数据的数据量
     *
     * @return map数据
     * @throws ParseException 日期转换
     */
    public Map<String, Integer> obtainBpmMeasureThisDayOrMonth() throws ParseException {
        List<String> list = Lists.newArrayList();
        Map<String, Integer> map = Maps.newHashMap();
        CustomerContextHolder.selectProdDataSource();
        for (int i = 0; i < TABLES; i++) {
            list.addAll(bpmBindStatisticMapper.obtainBpmMeasureThisMonth(i));
        }
        CustomerContextHolder.clearDataSource();
        map.put(MONTH_KEY, list.size());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        String dateStr = simpleDateFormat.format(new Date());
        int todayNUm = 0;
        for (String str : list) {
            String strDate = simpleDateFormat.format(simpleDateFormat.parse(str));
            if (strDate.equals(dateStr)) {
                todayNUm += 1;
            }
        }
        map.put(TODAY_KEY, todayNUm);
        logger.info(map);
        return map;


    }

}

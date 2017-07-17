package com.phicomm.product.manger.service;

import com.google.common.collect.Lists;
import com.phicomm.product.manger.dao.BalanceLocationMapper;
import com.phicomm.product.manger.dao.LianbiActiveMapper;
import com.phicomm.product.manger.model.statistic.BalanceLocation;
import com.phicomm.product.manger.model.statistic.BalanceLocationStatistic;
import com.phicomm.product.manger.model.statistic.BalanceSaleNumber;
import com.phicomm.product.manger.model.statistic.LocationCountBean;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 电子秤销售统计服务
 * <p>
 * Created by yufei.liu on 2017/6/23.
 */
@Service
public class BalanceLocationService {

    private BalanceLocationMapper balanceLocationMapper;

    private static final int LOCATION_PAGE_SIZE = 15;

    private LianbiActiveMapper lianbiActiveMapper;

    private static final int DEFAULT_MONTH = 12;

    private static final int DEFAULT_DAY = 30;

    @Autowired
    public BalanceLocationService(BalanceLocationMapper balanceLocationMapper,
                                  LianbiActiveMapper lianbiActiveMapper) {
        this.balanceLocationMapper = balanceLocationMapper;
        this.lianbiActiveMapper = lianbiActiveMapper;
        Assert.notNull(this.balanceLocationMapper);
        Assert.notNull(this.lianbiActiveMapper);
    }

    /**
     * 获取当天的统计以及总的地区统计
     */
    public BalanceLocationStatistic getBalanceLocationStatistic() {
        CustomerContextHolder.selectProdDataSource();
        List<BalanceLocation> totalBalanceLocations = balanceLocationMapper.getTotalBalanceLocation();
        List<BalanceLocation> currentDateBalanceLocations = balanceLocationMapper.getCurrentDateBalanceLocation(
                LocalDateTime.now().toString("yyyy-MM-dd 00:00:00"));
        int saleCount = balanceLocationMapper.getLocationTotalCount();
        CustomerContextHolder.clearDataSource();
        BalanceLocationStatistic balanceLocationStatistic = new BalanceLocationStatistic();
        balanceLocationStatistic.setLocationTotalCount(saleCount);
        balanceLocationStatistic.setTotalStatistic(format(totalBalanceLocations));
        balanceLocationStatistic.setCurrentDateStatistic(format(currentDateBalanceLocations));
        return balanceLocationStatistic;
    }

    /**
     * 将数据格式化
     */
    private List<BalanceLocation> format(List<BalanceLocation> balanceLocationList) {
        List<BalanceLocation> balanceLocations = Lists.newArrayList();
        balanceLocationList.forEach((location) -> {
            BalanceLocation balanceLocation = location.format();
            if (balanceLocation != null) {
                balanceLocations.add(balanceLocation);
            }
        });
        return balanceLocations;
    }

    /**
     * 获取电子秤销售数量
     */
    public BalanceSaleNumber getBalanceSaleNumber() {
        CustomerContextHolder.selectProdDataSource();
        int balanceSaleNumber = balanceLocationMapper.getBalanceSaleNumber();
        CustomerContextHolder.clearDataSource();
        BalanceSaleNumber result = new BalanceSaleNumber();
        result.setSaleNumber(balanceSaleNumber);
        return result;
    }

    /**
     * 获取N个月的位置信息
     */
    public Map<String, Integer> obtainLocationCountByMonth(int month, String type, int pageSize) {
        List<LocationCountBean> countBeans;
        month = month <= 0 ? DEFAULT_MONTH : month;
        pageSize = pageSize <= 0 ? LOCATION_PAGE_SIZE : pageSize;
        CustomerContextHolder.selectProdDataSource();
        //联璧激活的位置信息
        if ("lianbi".equals(type)) {
            countBeans = lianbiActiveMapper.obtainActiveLocationCountByMonth(month, pageSize);
        } else {
            countBeans = balanceLocationMapper.obtainLocationCountByMonth(month, pageSize);
        }
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> result = new LinkedHashMap<>();
        for (LocationCountBean countBean : countBeans) {
            result.put(countBean.getProvince(), countBean.getGenerateCount());
        }
        return result;
    }

    /**
     * 获取N天的位置信息
     */
    public Map<String, Integer> obtainLocationCountByDay(int day, String type, int pageSize) {
        List<LocationCountBean> countBeans;
        day = day <= 0 ? DEFAULT_DAY : day;
        pageSize = pageSize <= 0 ? LOCATION_PAGE_SIZE : pageSize;
        CustomerContextHolder.selectProdDataSource();
        //联璧激活位置信息
        if ("lianbi".equalsIgnoreCase(type)) {
            countBeans = lianbiActiveMapper.obtainActiveLocationCountByDay(day, pageSize);
        } else {
            //其它默认为电子秤位置信息
            countBeans = balanceLocationMapper.obtainLocationCountByDay(day, pageSize);
        }
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> result = new LinkedHashMap<>();
        for (LocationCountBean countBean : countBeans) {
            result.put(countBean.getProvince(), countBean.getGenerateCount());
        }
        return result;
    }
}

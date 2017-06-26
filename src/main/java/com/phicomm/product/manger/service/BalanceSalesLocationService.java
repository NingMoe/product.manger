package com.phicomm.product.manger.service;

import com.google.common.collect.Lists;
import com.phicomm.product.manger.dao.BalanceLocationMapper;
import com.phicomm.product.manger.model.statistic.BalanceLocation;
import com.phicomm.product.manger.model.statistic.BalanceLocationStatistic;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 电子秤销售统计服务
 * <p>
 * Created by yufei.liu on 2017/6/23.
 */
@Service
public class BalanceSalesLocationService {

    private static final Logger logger = Logger.getLogger(BalanceSalesLocationService.class);

    private final BalanceLocationMapper balanceLocationMapper;

    @Autowired
    public BalanceSalesLocationService(BalanceLocationMapper balanceLocationMapper) {
        this.balanceLocationMapper = balanceLocationMapper;
        Assert.notNull(this.balanceLocationMapper);
    }

    /**
     * 获取当天的统计以及总的地区统计
     */
    public BalanceLocationStatistic getBalanceLocationStatistic() {
        CustomerContextHolder.selectProdDataSource();
        List<BalanceLocation> totalBalanceLocations = balanceLocationMapper.getTotalBalanceLocation();
        List<BalanceLocation> currentDateBalanceLocations = balanceLocationMapper.getCurrentDateBalanceLocation(
                LocalDateTime.now().toString("yyyy-MM-dd 00:00:00"));
        CustomerContextHolder.clearDataSource();
        BalanceLocationStatistic balanceLocationStatistic = new BalanceLocationStatistic();
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
            if(balanceLocation != null) {
                balanceLocations.add(balanceLocation);
            } else {
                logger.error(location);
            }
        });
        return balanceLocations;
    }

}

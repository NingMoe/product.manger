package com.phicomm.product.manger.service;

import com.phicomm.product.manger.dao.BalanceStatusMapper;
import com.phicomm.product.manger.dao.BalanceUserManagerMapper;
import com.phicomm.product.manger.dao.LianbiActiveMapper;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.statistic.BalanceMacStatus;
import com.phicomm.product.manger.model.statistic.CountBean;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import com.phicomm.product.manger.utils.RegexUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 主要统计一下balance_status_info新增的量
 * Created by wei.yang on 2017/6/30.
 */
@Service
public class BalanceStatisticService {

    private BalanceUserManagerMapper balanceUserManagerMapper;

    private BalanceStatusMapper balanceStatusMapper;

    private LianbiActiveMapper lianbiActiveMapper;

    @Autowired
    public BalanceStatisticService(BalanceUserManagerMapper balanceUserManagerMapper,
                                   BalanceStatusMapper balanceStatusMapper,
                                   LianbiActiveMapper lianbiActiveMapper) {
        this.balanceUserManagerMapper = balanceUserManagerMapper;
        this.balanceStatusMapper = balanceStatusMapper;
        this.lianbiActiveMapper = lianbiActiveMapper;
        Assert.notNull(this.balanceUserManagerMapper);
        Assert.notNull(this.balanceStatusMapper);
        Assert.notNull(this.lianbiActiveMapper);

    }

    /**
     * 每个月的上传量:不包含今天
     *
     * @return 上传量
     */
    public Map<String, Integer> obtainCountByMonth(int month, String type) {
        DateTime dateTime = new DateTime(DateTime.now()).minusDays(1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        List<CountBean> countBeans;
        CustomerContextHolder.selectProdDataSource();
        if ("lianbi".equalsIgnoreCase(type)) {
            countBeans = lianbiActiveMapper.obtainActiveCountByMonth(month);
        } else {
            countBeans = balanceStatusMapper.obtainCountByMonth(month);
        }
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> result = new TreeMap<>();
        for (CountBean countBean : countBeans) {
            result.put(countBean.getGenerateTime(), countBean.getGenerateCount());
        }
        for (int i = 0; i < month; i++) {
            String generateTime = dateFormat.format(dateTime.toDate());
            if (!result.containsKey(generateTime)) {
                result.put(generateTime, 0);
            }
            dateTime = dateTime.minusMonths(1);
        }

        return result;
    }

    /**
     * 获取N天的上传量
     *
     * @return 上传量
     */
    public Map<String, Integer> obtainCountByDay(int day, String type) {
        DateTime dateTime = new DateTime(DateTime.now()).minusDays(1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<CountBean> countBeans;
        CustomerContextHolder.selectProdDataSource();
        if ("lianbi".equalsIgnoreCase(type)) {
            countBeans = lianbiActiveMapper.obtainActiveCountByDay(day);
        } else {
            countBeans = balanceStatusMapper.obtainCountByDay(day);
        }
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, Integer> result = new TreeMap<>();
        for (CountBean countBean : countBeans) {
            result.put(countBean.getGenerateTime(), countBean.getGenerateCount());
        }
        for (int i = 0; i < day; i++) {
            String generateTime = dateFormat.format(dateTime.toDate());
            if (!result.containsKey(generateTime)) {
                result.put(generateTime, 0);
            }
            dateTime = dateTime.minusDays(1);
        }

        return result;
    }

    /**
     * 根据mac地址获取关于这个mac地址的相关信息
     *
     * @param mac mac地址
     * @return mac的相关信息
     * @throws DataFormatException mac地址
     */
    public BalanceMacStatus obtainMacInfo(String mac) throws DataFormatException {
        boolean right = RegexUtil.checkMacFormat(mac);
        BalanceMacStatus balanceMacStatus = new BalanceMacStatus();
        if (!right) {
            throw new DataFormatException();
        }
        CustomerContextHolder.selectProdDataSource();
        balanceMacStatus.setActiveCity(lianbiActiveMapper.obtainActiveCity(mac))
                .setCreateTime(balanceStatusMapper.obtainStatusCreateTime(mac))
                .setMemberCount(balanceUserManagerMapper.obtainMemberCount(mac));
        CustomerContextHolder.clearDataSource();
        return balanceMacStatus;
    }
}

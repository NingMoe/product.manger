package com.phicomm.product.manger.service;

import com.phicomm.product.manger.dao.BpmSaleStatisticMapper;
import com.phicomm.product.manger.model.bpm.BpmCountBean;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 统计血压计的销量
 * Created by yafei.hou on 2017/11/9.
 *
 * @author yafei.hou
 */
@Service
public class BpmSaleStatisticService {

    private Logger logger = Logger.getLogger(BpmSaleStatisticService.class);

    private BpmSaleStatisticMapper bpmSaleStatisticMapper;

    @Autowired
    public BpmSaleStatisticService(BpmSaleStatisticMapper bpmSaleStatisticMapper) {
        this.bpmSaleStatisticMapper = bpmSaleStatisticMapper;
        Assert.notNull(this.bpmSaleStatisticMapper);
    }

    /**
     * 获取前N个月的血压计每个月的销量
     *
     * @param months 月数
     * @return Map\<String, Integer>月份和数量的关系表
     */
    public Map<String, Integer> obtainBPMSaleNumByMonth(int months) {
        DateTime dateTime = new DateTime(DateTime.now());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM");
        List<BpmCountBean> countBeans;
        CustomerContextHolder.selectProdDataSource();
        countBeans = bpmSaleStatisticMapper.obtainBpmSaleByMonth(months);
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>(0);
        }
        Map<String, Integer> resultMonth = new TreeMap<>();
        for (BpmCountBean countBean : countBeans) {
            resultMonth.put(countBean.getBindPBMTime(), countBean.getBindPBMCount());
        }
        int i = 0;
        while (i < months) {
            String bindPBMTime = dateFormat.format(dateTime.toDate());
            if (!resultMonth.containsKey(bindPBMTime)) {
                resultMonth.put(bindPBMTime, 0);
            }
            dateTime = dateTime.minusMonths(1);
            i++;
        }
        return resultMonth;
    }


    /**
     * 获取前N天的血压计每天的销量
     *
     * @param days 天数
     * @return Map\<String, Integer>天数和数量的关系表
     */
    public Map<String, Integer> obtainBPMSaleNumByDay(int days) {
        DateTime dateTime = new DateTime(DateTime.now());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        List<BpmCountBean> countBeans;
        CustomerContextHolder.selectProdDataSource();
        countBeans = bpmSaleStatisticMapper.obtainBpmSaleNumByDay(days);
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>(0);
        }
        Map<String, Integer> result = new TreeMap<>();
        for (BpmCountBean countBean : countBeans) {
            result.put(countBean.getBindPBMTime(), countBean.getBindPBMCount());
        }
        int i = 0;
        while (i < days) {
            String bindPBMTime = dateFormat.format(dateTime.toDate());
            if (!result.containsKey(bindPBMTime)) {
                result.put(bindPBMTime, 0);
            }
            dateTime = dateTime.minusDays(1);
            i++;
        }
        logger.info("obtainBPMSaleNumByDay\n"+result);
        return result;
    }

    /**
     * 统计血压计总销量（包括今天）
     *
     * @return 总销量
     */
    public int obtainBPMSaleNumAll() {
        CustomerContextHolder.selectProdDataSource();
        int result = bpmSaleStatisticMapper.obtainBpmSaleNumAll();
        CustomerContextHolder.clearDataSource();
        return result;
    }

    /**
     * 统计血压计 今天销量（包括今天)
     *
     * @return 总销量
     */
    public int obtainBPMSaleNumToday() {
        CustomerContextHolder.selectProdDataSource();
        int result = bpmSaleStatisticMapper.obtainBpmSaleNumToday();
        CustomerContextHolder.clearDataSource();
        return result;
    }
}

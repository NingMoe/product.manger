package com.phicomm.product.manger.service;

import com.phicomm.product.manger.dao.BPMSaleStatisticMapper;
import com.phicomm.product.manger.model.bpmstatistic.BPMCountBean;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
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
 */
@Service
public class BPMSaleStatisticService {

    private BPMSaleStatisticMapper bpmSaleStatisticMapper;

    @Autowired
    public BPMSaleStatisticService(BPMSaleStatisticMapper bpmSaleStatisticMapper) {
        this.bpmSaleStatisticMapper = bpmSaleStatisticMapper;
        Assert.notNull(bpmSaleStatisticMapper);
    }

    //获取前N个月的血压计每个月的销量
    public Map<String , Integer> obtainBPMSaleNumByMonth(int months){
        DateTime dateTime = new DateTime(DateTime.now());
        SimpleDateFormat dateFormat= new SimpleDateFormat("yy-MM");
        List<BPMCountBean> countBeans ;
        CustomerContextHolder.selectProdDataSource();
        countBeans = bpmSaleStatisticMapper.obtainBPMSaleNumByMonth(months);
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> resultMonth = new TreeMap<>();
        for (BPMCountBean countBean : countBeans) {
            resultMonth.put(countBean.getBindPBMTime(), countBean.getBindPBMCount());
        }
        for (int i=0; i <months; i++) {
            String bindPBMTime = dateFormat.format(dateTime.toDate());
            if (!resultMonth.containsKey(bindPBMTime)) {
                resultMonth.put(bindPBMTime, 0);
            }else{}
            dateTime = dateTime.minusMonths(1) ;
        }
        return resultMonth;
    }


    //获取前N天的血压计每天的销量
    public Map<String , Integer> obtainBPMSaleNumByDay(int days){
        DateTime dateTime = new DateTime(DateTime.now());
        SimpleDateFormat dateFormat= new SimpleDateFormat("yy-MM-dd");
        List<BPMCountBean> countBeans ;
        CustomerContextHolder.selectProdDataSource();
        countBeans = bpmSaleStatisticMapper.obtainBPMSaleNumByDay(days);
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> resultMonth = new TreeMap<>();
        for (BPMCountBean countBean : countBeans) {
            resultMonth.put(countBean.getBindPBMTime(), countBean.getBindPBMCount());
        }
        for (int i=0; i <days; i++) {
            String bindPBMTime = dateFormat.format(dateTime.toDate());
            if (!resultMonth.containsKey(bindPBMTime)) {
                resultMonth.put(bindPBMTime, 0);
            }else{}
            dateTime = dateTime.minusDays(1) ;
        }
        return resultMonth;
    }

    //统计血压计总销量（包括今天）
    public int obtainBPMSaleNumAll(){
        CustomerContextHolder.selectProdDataSource();
        int result = bpmSaleStatisticMapper.obtainBPMSaleNumAll();
        CustomerContextHolder.clearDataSource();
        return result;
    }
    //统计血压计 今天销量（包括今天）
    public int obtainBPMSaleNumToday(){
        CustomerContextHolder.selectProdDataSource();
        int result = bpmSaleStatisticMapper.obtainBPMSaleNumToday();
        CustomerContextHolder.clearDataSource();
        return result;
    }
}

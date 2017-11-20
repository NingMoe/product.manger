package com.phicomm.product.manger.service;

import com.google.common.collect.HashMultimap;
import com.phicomm.product.manger.dao.BalanceDailyStatisticMapper;
import com.phicomm.product.manger.model.statistic.Balance24HourDisplayQueryResultModel;
import com.phicomm.product.manger.model.statistic.BalanceAsHourModel;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import io.swagger.models.auth.In;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.*;


/**
 * Created by song02.cao on 2017/11/16.
 */

@Service
public class BalanceDailyStatisticService {

    private static final Logger logger = Logger.getLogger(BalanceDailyStatisticService.class);

    private static final int BALANCE_MEASURE_TABLE_NUM = 50;

    private static final int DISPLAY_HOURS = 24;

    private static final String[] HOURS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
            "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};

    private BalanceDailyStatisticMapper balanceDailyStatisticMapper;

    @Autowired
    public BalanceDailyStatisticService(BalanceDailyStatisticMapper balanceDailyStatisticMapper) {
        this.balanceDailyStatisticMapper = balanceDailyStatisticMapper;
        Assert.notNull(this.balanceDailyStatisticMapper);
    }

    public void setBalanceStatisticHourUnit() {
        HashMultimap<String, Integer> hashMultimap = HashMultimap.create();
        Map<String, Integer> map = new HashMap<>();
        List<BalanceAsHourModel> balanceAsHourModelsList = new ArrayList<>();

        for (int i = 1; i < BALANCE_MEASURE_TABLE_NUM; i++) {
            List<BalanceAsHourModel> hourStatisticList = new ArrayList<>();
            HashMultimap<String, Integer> newMap = HashMultimap.create();
            hourStatisticList.addAll(balanceDailyStatisticMapper.getBalanceStatistic24HourDisplay(i));
            newMap = list2HashMultiMap(hourStatisticList);
            hashMultimap.putAll(newMap);
            hashMultimap = sumSameKeyValue(hashMultimap);
        }
        map = hashMultiMap2HashMap(hashMultimap);
        balanceAsHourModelsList = map2List(map);
        for (BalanceAsHourModel balanceAsHourModel : balanceAsHourModelsList) {
            balanceDailyStatisticMapper.setBalanceStatisticHourUnit(balanceAsHourModel.getCount(), balanceAsHourModel.getTimeAsHour());
        }

    }

    public Balance24HourDisplayQueryResultModel get24HourDisplay() {
        List<BalanceAsHourModel> hourModelList = new ArrayList<>();
        Balance24HourDisplayQueryResultModel resultModel = new Balance24HourDisplayQueryResultModel();
        CustomerContextHolder.selectTestDataSource();
        hourModelList = balanceDailyStatisticMapper.getBalance24HourcCount();
        CustomerContextHolder.clearDataSource();
        int size = hourModelList.size();
        String[] hours = new String[24];
        int[] counts = new int[24];
        //@todo 这段代码写的太垃圾了,自己都看不下去了。。。当前先把功能完成，后续优化
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < 24; i++) {
            map.put(i,0);
        }
        for (int i = 0; i < size; i++) {
            map.put(Integer.parseInt(hourModelList.get(i).getTimeAsHour()),hourModelList.get(i).getCount());
        }
        for (int key:map.keySet()) {
            hours[key] = String.valueOf(key);
            counts[key] = map.get(key);
        }
        resultModel.setHours(hours);
        resultModel.setCounts(counts);
        return resultModel;
    }

    private HashMultimap<String, Integer> list2HashMultiMap(List<BalanceAsHourModel> list) {
        Assert.notNull(list);
        HashMultimap<String, Integer> map = HashMultimap.create();
        for (BalanceAsHourModel balanceAsHourModel : list) {
            map.put(balanceAsHourModel.getTimeAsHour(), balanceAsHourModel.getCount());
        }
        return map;
    }


    private Map<String, Integer> hashMultiMap2HashMap(HashMultimap<String, Integer> multimap) {
        Assert.notNull(multimap);
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String key : multimap.keySet()) {
            int value = 0;
            Collection<Integer> valSet = multimap.get(key);
            Iterator<Integer> iterator = valSet.iterator();
            while (iterator.hasNext()) {
                value = iterator.next();
            }
            map.put(key, value);
        }
        return map;
    }

    private List<BalanceAsHourModel> map2List(Map<String, Integer> map) {
        Assert.notNull(map);
        List<BalanceAsHourModel> list = new ArrayList<>();
        for (String key : map.keySet()) {
            BalanceAsHourModel balanceAsHourModel = new BalanceAsHourModel();
            balanceAsHourModel.setTimeAsHour(key);
            balanceAsHourModel.setCount(map.get(key));
            list.add(balanceAsHourModel);
        }
        return list;
    }

    private HashMultimap<String, Integer> sumSameKeyValue(HashMultimap<String, Integer> map) {
        Assert.notNull(map);
        HashMultimap<String, Integer> newMap = HashMultimap.create();
        for (String key : map.keySet()) {
            Collection<Integer> values = map.get(key);
            Integer valueSum = 0;
            for (Integer value : values) {
                valueSum += value;
            }
            newMap.put(key, valueSum);
        }
        return newMap;
    }

}
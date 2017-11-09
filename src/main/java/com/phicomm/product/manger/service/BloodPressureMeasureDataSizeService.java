package com.phicomm.product.manger.service;

import com.google.common.collect.Lists;
import com.phicomm.product.manger.dao.BloodPressureMeasureDataSizeMapper;
import com.phicomm.product.manger.model.bpmstatistic.BPMMeasureBean;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * Created by yafei.hou on 2017/11/8.
 */
@Service
public class BloodPressureMeasureDataSizeService {

    private static final Logger logger = Logger.getLogger(BloodPressureMeasureDataSizeService.class);

    private static final int LIMIT_MONTH_NUM = 12;
    private static final int LIMIT_DAY_NUM = 15;


    private BloodPressureMeasureDataSizeMapper bloodPressureMeasureDataSizeMapper;

    private BPMStatisticService bpmStatisticService ;

    @Autowired
    public BloodPressureMeasureDataSizeService(BloodPressureMeasureDataSizeMapper bloodPressureMeasureDataSizeMapper,
                                               BPMStatisticService bpmStatisticService) {
        this.bpmStatisticService = bpmStatisticService ;
        this.bloodPressureMeasureDataSizeMapper = bloodPressureMeasureDataSizeMapper;
        cronTask() ;
    }

    /**
     * 每天自动执行统计信息
     */
    public void cronTask(){

        for (BPMMeasureBean beanMonth : msp2List(statisticMeasureMonth())) {
            CustomerContextHolder.selectLocalDataSource();
            bloodPressureMeasureDataSizeMapper.insertMonth(beanMonth);
            CustomerContextHolder.clearDataSource();
        }
        logger.info("更新blood_pressure_measure_data_size_every_month表");
        for (BPMMeasureBean beanDay : msp2List(statisticMeasureDay())) {
            CustomerContextHolder.selectLocalDataSource();
            bloodPressureMeasureDataSizeMapper.insertDay(beanDay);
            CustomerContextHolder.clearDataSource();
        }
        logger.info("更新blood_pressure_measure_data_size_every_day表");
        for (BPMMeasureBean beanHour : msp2List(statisticMeasureHour())) {
            CustomerContextHolder.selectLocalDataSource();
            bloodPressureMeasureDataSizeMapper.insertHour(beanHour);
            CustomerContextHolder.clearDataSource();
        }
        logger.info("更新blood_pressure_measure_data_size_every_hour表");

    }

    private Map<String, Integer> statisticMeasureMonth(){
        return bpmStatisticService.obtainBPMeasureCountByMonth(LIMIT_MONTH_NUM);
    }

    private Map<String, Integer> statisticMeasureDay(){
        return bpmStatisticService.obtainBPMeasureCountByDay(LIMIT_DAY_NUM);
    }

    private Map<String, Integer> statisticMeasureHour(){
        return bpmStatisticService.obtainBPMeasureCountByHour();
    }

    private List<BPMMeasureBean> msp2List(Map<String, Integer> map){
        List<BPMMeasureBean> list = Lists.newArrayList();
        if(map==null || map.size()<0){ return null ;}
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            list.add(new BPMMeasureBean(entry.getKey(),entry.getValue()));
        }
        return list;
    }

    //查询服务
    public Map<String, Integer> bpmMeasureDataSizeByMonth(){

        CustomerContextHolder.selectLocalDataSource();
        List<BPMMeasureBean> list = bloodPressureMeasureDataSizeMapper.selectMonth(LIMIT_MONTH_NUM);
        CustomerContextHolder.clearDataSource();
        return getStringIntegerMap(list);
    }

    public Map<String, Integer> bpmMeasureDataSizeByDay(){
        CustomerContextHolder.selectLocalDataSource();
        List<BPMMeasureBean> list = bloodPressureMeasureDataSizeMapper.selectDay(LIMIT_DAY_NUM);
        CustomerContextHolder.clearDataSource();
        return getStringIntegerMap(list);
    }

    public Map<String, Integer> bpmMeasureDataSizeByHour(){
        CustomerContextHolder.selectLocalDataSource();
        List<BPMMeasureBean> list = bloodPressureMeasureDataSizeMapper.selectHour(24);
        CustomerContextHolder.clearDataSource();
        return getStringIntegerMap(list);
    }

    @NotNull
    private Map<String, Integer> getStringIntegerMap(List<BPMMeasureBean> list) {
        Map<String, Integer> map =  new TreeMap<>();
        if (list==null || list.size()<=0) { return new HashMap<>();}
        for (BPMMeasureBean bean : list) {
            map.put(bean.getMeasureTime(),bean.getMeasureCount());
        }
        logger.info(map);
        return  map;
    }

}

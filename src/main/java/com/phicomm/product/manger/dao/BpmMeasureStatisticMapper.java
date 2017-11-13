package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.bpm.BpmMeasureBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  血压计用户测量数据统计
 * Created by yafei.hou on 2017/11/8.
 * @author yafei.hou
 *
 */
@Repository
public interface BpmMeasureStatisticMapper {


    /**
     * 插入数据--每个月的测量数
     * @param bpmMeasureBean 测量数据bean
     */
    void insertMonth(@Param("bpmMeasureBean")BpmMeasureBean bpmMeasureBean);
    /**
     * 插入数据--每个月的测量数
     * @param bpmMeasureBean 测量数据bean
     */
    void insertDay(@Param("bpmMeasureBean")BpmMeasureBean bpmMeasureBean);
    /**
     * 插入数据--每个月的测量数
     * @param bpmMeasureBean 测量数据bean
     */
    void insertHour(@Param("bpmMeasureBean")BpmMeasureBean bpmMeasureBean);

    /**
     * 查询最近N个月的测量数据
     * @return List<BpmMeasureBean> 查询数据列表
     */
    List<BpmMeasureBean> selectMonth();
    /**
     * 查询最近N个天的测量数据
     * @return List<BpmMeasureBean> 查询数据列表
     */
    List<BpmMeasureBean> selectDay();
    /**
     * 查询24小时的测量数据分布
     * @return List<BpmMeasureBean> 查询数据列表
     */
    List<BpmMeasureBean> selectHour();



}

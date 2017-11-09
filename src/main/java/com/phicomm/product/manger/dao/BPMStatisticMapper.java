package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.bpmstatistic.BPMCountBean;
import com.phicomm.product.manger.model.bpmstatistic.BPMMeasureBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Created by yafei.hou on 2017/11/6.
 */
@Repository
public interface BPMStatisticMapper {

    /**
     * 获取最近N天的血压计绑定数量
     * @param days 整数，最近的天数
     * @return 数组列表
     */
    List<BPMCountBean> obtainBPMCountByDay(@Param("days") int days);
    //获取最近N月的血压计绑定数量
    List<BPMCountBean> obtainBPMCountByMonth(@Param("months") int months);
    //绑定的血压计总数
    int obtainBPMCountAll();

    /**
     * 获取最近N天的血压计数据测量的数目
     * @param tableNum 表格的编号
     * @param dayNum 整数，最近的天数
     * @return 数组列表
     */
    List<BPMMeasureBean> obtainBPMeasureCountByDay(@Param("table") int tableNum,@Param("day") int dayNum);

    /**
     * 获取最近N月的血压计数据测量的数目
     * @param tableNum 表格的编号
     * @param month 整数，最近的月数
     * @return 数组列表
     */
    List<BPMMeasureBean> obtainBPMeasureCountByMonth(@Param("table") int tableNum,@Param("month") int month);

    /**
     * 获取最近血压计数据测量的数目 24小时 时间分布（每小时）
     * @param tableNum 表格的编号
     * @return 数组列表
     */
    List<BPMMeasureBean> obtainBPMeasureTime(@Param("table") int tableNum);

    /**
     * 血压计数据测量数据总量
     * @param tableNum 表格的编号
     * @return 整数，数量
     */
    int obtainBPMeasureCounts(@Param("table") int tableNum);

    /**
     * 血压计数据测量数据总量（当前月份）
     * @param tableNum 表格的编号
     * @return 数量
     */
    List<String> obtainBPMeasureCountsThisMonth(@Param("table") int tableNum);


}

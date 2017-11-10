package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.bpm.BpmCountBean;
import com.phicomm.product.manger.model.bpm.BpmMeasureBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 血压计绑定数量统计，以及血压计测量数据统计（smart_hardware数据库中的20张表中查询）
 * Created by yafei.hou on 2017/11/6.
 * @author yafei.hou
 */
@Repository
public interface BpmBindStatisticMapper {

    /**
     * 获取最近N天的血压计绑定数量
     * @param days 整数，最近的天数
     * @return 数组列表
     */
    List<BpmCountBean> obtainBpmCountByDay(@Param("days") int days);

    /**
     * 获取最近N月的血压计绑定数量
     * @param months 整数，最近的天数
     * @return 数组列表
     */
    List<BpmCountBean> obtainBpmCountByMonth(@Param("months") int months);

    /**
     * 绑定的血压计总数
     * @return 绑定的血压计总数
     */
    int obtainBpmCountAll();

    /**
     * 获取最近N天的血压计数据测量的数目
     * @param tableNum 表格的编号
     * @param dayNum 整数，最近的天数
     * @return 数组列表
     */
    List<BpmMeasureBean> obtainBpmMeasureByDay(@Param("table") int tableNum, @Param("day") int dayNum);

    /**
     * 获取最近N月的血压计数据测量的数目
     * @param tableNum 表格的编号
     * @param month 整数，最近的月数
     * @return 数组列表
     */
    List<BpmMeasureBean> obtainBpmMeasureByMonth(@Param("table") int tableNum, @Param("month") int month);

    /**
     * 获取最近血压计数据测量的数目 24小时 时间分布（每小时）
     * @param tableNum 表格的编号
     * @return 数组列表
     */
    List<BpmMeasureBean> obtainBpmMeasureTime(@Param("table") int tableNum);

    /**
     * 血压计数据测量数据总量
     * @param tableNum 表格的编号
     * @return 整数，数量
     */
    int obtainBpmMeasureCounts(@Param("table") int tableNum);

    /**
     * 血压计数据测量数据总量（当前月份）
     * @param tableNum 表格的编号
     * @return 数量
     */
    List<String> obtainBpmMeasureThisMonth(@Param("table") int tableNum);


}

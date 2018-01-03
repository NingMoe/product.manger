package com.phicomm.product.manger.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 实现s7数据报告的类数据接口
 *
 * @author yafei.hou
 *         Created by yafei.hou on 2017/12/26.
 */
@Repository
public interface S7reportsMapper {

    /**
     * 将各个厂家每日激活s7数量添加到数据库中
     *
     * @param date 日期，格式为yyyy-mm-dd
     * @param num1 联璧激活数量
     * @param num2 万家金服激活数量
     */
    void addedActivationCount(@Param("date") String date,
                              @Param("lianbi") long num1, @Param("wanjia") long num2);

    /**
     * 获取当前月份内各个厂家每天激活数据量
     *
     * @return 激活数据
     */
    List<Map<String, Object>> obtainActivationStatisticThisMonth();

    /**
     * 更新月份统计表
     *
     * @param date 月份 格式yyyy-mm
     * @param num1 联璧当月总数
     * @param num2 万家金服当月激活总数
     */
    void upDateActivationMonthCount(@Param("month") String date,
                                    @Param("lianbi") long num1, @Param("wanjia") long num2);


    /**
     * 获取最近N天的激活情况统计（每个厂商激活数量）
     *
     * @param num 最近的天数
     * @return map数据表
     */
    List<Map<String, Object>> obtainActivationStatisticEveryDay(@Param("num") int num);

    /**
     * 最近N天每天激活总量
     *
     * @param num 最近的天数
     * @return map数据表
     */
    List<Map<String, Object>> obtainActivationStatisticByDay(@Param("num") int num);

    /**
     * 获取某天各个厂家激活状况
     *
     * @param date 当前日期 yyyy-mm-dd
     * @return 获取某天各个厂家激活状况
     */
    List<Map<String, Object>> obtainActivationStatisticDay(@Param("date") String date);

    /**
     * 获取最近N月的激活情况统计
     *
     * @param month 最近n个月的情况
     * @return map数据表
     */
    List<Map<String, Object>> obtainActivationStatisticMonth(@Param("num") int month);

    /**
     * 获取每个厂家激活总量
     *
     * @return 激活总量
     */
    List<Map<String, Object>> obtainActivationAllCounts();

    /**
     * 添加android和ios设备每日用户活跃数量
     *
     * @param date    日期，格式为yyyy-mm-dd
     * @param ios     ios用户活跃用户数量
     * @param android android 活跃用户数量
     */
    void addedActiveUsersCount(@Param("date") String date,
                               @Param("ios") long ios, @Param("android") long android);

    /**
     * 获取某天各个设备的用户活跃量
     *
     * @param date 日期 格式为yyyy-mm-dd
     * @return 获取某天各个设备的用户活跃量
     */
    List<Map<String, Object>> activeUsersCount(@Param("date") String date);

    /**
     * 获取最近N天用户活跃度
     *
     * @param num 最近N天
     * @return 获取最近N天用户活跃度
     */
    List<Map<String, Object>> activeUsersCountEveryDay(@Param("num") int num);

}

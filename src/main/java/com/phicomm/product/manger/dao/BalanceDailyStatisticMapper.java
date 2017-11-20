package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.statistic.BalanceAsHourModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by song02.cao on 2017/11/16.
 */

@Repository
public interface BalanceDailyStatisticMapper {

    /**
     *  以小时为单位获取某个时间范围内的统计数
     * @param startTime                     开始时间
     * @param endTime                       结束时间
     * @param tableIndex                    分表名称索引
     * @return List<BalanceAsHourModel>      返回的{时间，统计数}列表
     */
    List<BalanceAsHourModel> getBalanceStatisticHourUnitTimeRange(@Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("tableIndex")int tableIndex);

    /**
     *  以小时为单位获取某一条整天内的统计数
     * @param date                         日期
     * @param tableIndex                  分表名称索引
     * @return List<BalanceAsHourModel>    返回{时间，统计数}列表
     */
    List<BalanceAsHourModel> getBalanceStatisticHourUnitOneDay(@Param("date")Date date, @Param("tableIndex")int tableIndex);

    /**
     *  以小时为单位获取某所有天内的统计数
     * @param tableIndex                  分表名称索引
     * @return List<BalanceAsHourModel>    返回{时间，统计数}列表
     */
    List<BalanceAsHourModel> getBalanceStatisticHourUnitAll(@Param("tableIndex")int tableIndex);

    /**
     *  以小时为单位获取某所有天内的统计数的和
     * @param tableIndex                  分表名称索引
     * @return List<BalanceAsHourModel>    返回{时间，统计数}列表
     */
    List<BalanceAsHourModel> getBalanceStatistic24HourDisplay(@Param("tableIndex")int tableIndex);

    /**
     *  将以小时为单位的统计写入到balance_measure_count_hour_unit表中
     * @param count
     * @param time
     */
    void setBalanceStatisticHourUnit(@Param("count")int count, @Param("time") String time);

    /**
     *  将表balance_measure_count_hour_unit中的数据读取，用于网页的显示
     * @return List<BalanceAsHourModel>  返回{时间，统计数}列表
     */
    List<BalanceAsHourModel> getBalance24HourcCount();
}
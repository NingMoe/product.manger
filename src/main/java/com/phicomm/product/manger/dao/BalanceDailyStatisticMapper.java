package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.statistic.BalanceAsHourModel;
import com.phicomm.product.manger.model.statistic.BalanceElectrodeModel;
import com.phicomm.product.manger.model.statistic.BalanceElectrodeOutModel;
import com.phicomm.product.manger.model.statistic.BalanceMaxMinDatesModel;
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
     * 以小时为单位获取某所有天内的统计数的和
     *
     * @param tableIndex 分表名称索引
     * @return List<BalanceAsHourModel>    返回{时间，统计数}列表
     */
    List<BalanceAsHourModel> getBalanceStatistic24HourDisplay(@Param("tableIndex") int tableIndex);

    /**
     * 单独从table  balance_measure_info中获取24小时分布数据
     *
     * @return List<BalanceAsHourModel>    返回{时间，统计数}列表
     */
    List<BalanceAsHourModel> getBalanceStatistic24HourDisplaySpecialTable();

    /**
     * 将以小时为单位的统计写入到balance_measure_24_hour_count表中
     *
     * @param count
     * @param time
     */
    void setBalance24HourCount(@Param("count") int count,
                               @Param("time") String time);

    /**
     * 将表balance_measure_24_hour_count中的数据读取，用于网页的显示
     *
     * @return List<BalanceAsHourModel>  返回{时间，统计数}列表
     */
    List<BalanceAsHourModel> getBalance24HourCount();

    /**
     *  将表balance_measure_24_hour_count中的数据全部删除
     */
    void deleteBalance24HourCount();

    /**
     * 从原始表balance_measure_info_<date>获取一天的电极统计数据
     *
     * @param oneDay                        日期
     * @param tableIndex                    表索引
     * @return List<BalanceElectrodeModel>   返回{日期，电极，数量}对象
     */
    List<BalanceElectrodeModel> getBalanceElectrodeInfoOnedayFromOriginal(@Param("oneDay") Date oneDay,
                                                                          @Param("tableIndex") int tableIndex);

    /**
     * 从特殊原始表balance_measure_info获取一天的电极统计数据
     *
     * @param oneDay                        日期
     * @return List<BalanceElectrodeModel>   返回{日期，电极，数量}对象
     */
    List<BalanceElectrodeModel> getBalanceElectrodeInfoOnedayFromSpecialTable(@Param("oneDay") Date oneDay);

    /**
     * 将从原始表balance_measure_info中获取的电极相关插入到表balance_measure_electrode_statistic_each_day中
     * @param statisticDate             测量日期
     * @param electrode0Number           0电极数
     * @param electrode4Number          4电极数
     * @param electrode8Number          8电极数
     */
    void setBalanceElectrodeStatistic(@Param("statisticDate") Date statisticDate,
                                      @Param("electrode0Number")int electrode0Number,
                                      @Param("electrode4Number") int electrode4Number,
                                      @Param("electrode8Number") int electrode8Number);

    /**
     *  从数据表balance_measure_electrode_statistic_each_day中获取体脂秤电极情况
     * @param baseDay       基准时间点
     * @param dayRange      时间跨度
     * @return
     */
    List<BalanceElectrodeOutModel> getBalanceElectrodeInfoDayFrame(@Param("baseDay") Date baseDay,
                                                                   @Param("dayRange") int dayRange);


    /**
     *  获取表中最大、最小时间
     *  todo 暂时未使用到，后续根据情况进行删除或者保留
     *
     * @param tableIndex                表索引
     * @return BalanceMaxMinDatesModel   {maxDate,minDate}对象
     */
    BalanceMaxMinDatesModel getBalanceMaxMinDates(@Param("tableIndex") int tableIndex);
}
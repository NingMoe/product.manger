package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.statistic.BalanceActiveQueryModel;
import com.phicomm.product.manger.model.statistic.BalanceActiveStatisticModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 体脂秤日活统计
 *
 * @author yufei.liu
 */
@Repository
public interface BalanceActiveStatisticMapper {

    /**
     * 统计(左闭右开)
     *
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param tableIndex 表索引
     * @return 单表的count
     */
    @Select("SELECT count(1) FROM balance_mac_measure_info_${tableIndex} WHERE create_time >= #{startTime,jdbcType=TIMESTAMP} AND create_time < #{endTime,jdbcType=TIMESTAMP}")
    long statisticForPv(@Param("startTime") Date startTime,
                        @Param("endTime") Date endTime,
                        @Param("tableIndex") int tableIndex);

    /**
     * 统计(左闭右开)
     *
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param tableIndex 表索引
     * @return 单表的count
     */
    @Select("SELECT count(DISTINCT mac) FROM balance_mac_measure_info_${tableIndex} WHERE create_time >= #{startTime,jdbcType=TIMESTAMP} AND create_time < #{endTime,jdbcType=TIMESTAMP}")
    long statisticForUv(@Param("startTime") Date startTime,
                        @Param("endTime") Date endTime,
                        @Param("tableIndex") int tableIndex);

    /**
     * 保存
     *
     * @param statisticDate 统计的日期
     * @param sum           和
     */
    @Insert("INSERT IGNORE INTO balance_active_statistic_info(`date`, `active_count_pv`, `active_count_uv`) VALUES (#{statisticDate,jdbcType=TIMESTAMP},#{sum.pv,jdbcType=BIGINT},#{sum.uv,jdbcType=BIGINT})")
    void insert(@Param("statisticDate") Date statisticDate,
                @Param("sum") BalanceActiveStatisticModel sum);

    /**
     * 获取画图表的数据
     *
     * @param showBalanceMeasureDateNumber 需要统计最近的天数
     * @return 获取画图表的数据
     */
    @Select("SELECT active_count_uv AS `activeCountUv`, active_count_pv AS `activeCountPv`, `date` AS `date` FROM balance_active_statistic_info ORDER BY `date` ASC LIMIT #{showBalanceMeasureDateNumber,jdbcType=INTEGER}")
    List<BalanceActiveQueryModel> getDrawChartData(@Param("showBalanceMeasureDateNumber") int showBalanceMeasureDateNumber);

}

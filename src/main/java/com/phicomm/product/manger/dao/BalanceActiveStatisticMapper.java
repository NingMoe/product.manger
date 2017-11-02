package com.phicomm.product.manger.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;

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
    @Select("SELECT count(1) FROM balance_mac_measure_info_${tableIndex} WHERE create_time >= #{startTime,jdbcType=TIMESTAMP} AND create_time < #{startTime,jdbcType=TIMESTAMP}")
    long statistic(@Param("startTime") Date startTime,
                   @Param("endTime") Date endTime,
                   @Param("tableIndex") int tableIndex);

    /**
     * 保存
     * @param statisticDate 统计的日期
     * @param sum 和
     */
    @Insert("INSERT IGNORE INTO balance_active_statistic_info(`date`, `active_count`) VALUES (#{statisticDate,jdbcType=TIMESTAMP},#{sum,jdbcType=BIGINT})")
    void insert(@Param("statisticDate") Date statisticDate,
                @Param("sum") long sum);
}

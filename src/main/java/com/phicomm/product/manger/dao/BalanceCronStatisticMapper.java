package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.statistic.BalanceAccountInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 体脂秤定时任务结果表
 *
 * @author song02.cao
 */
@Repository
public interface BalanceCronStatisticMapper {

    /**
     * 获取体脂称账号统计信息
     *
     * @return 账号统计信息信息
     */
    BalanceAccountInfo getBalanceAccountInfo();

    /**
     * 往体脂称账号统计信息表中插入数据
     *
     * @param userCount   用户数量
     * @param memberCount 用户成员数量
     * @param macCount    mac数量
     */
    void insertUpateBalanceAccountInfo(@Param("userCount") int userCount,
                                       @Param("memberCount") int memberCount,
                                       @Param("macCount") int macCount);

    /**
     * 获取用户性别统计信息
     *
     * @return 用户性别信息
     */
    @MapKey("gender")
    Map<String, Map<String, Integer>> getBalanceUserCountInfo();

    /**
     * 往用户性别信息表中插入数据
     *
     * @param id     表中id
     * @param gender 性别
     * @param count  数量
     */
    void insertUpdateBalanceUserCountInfo(@Param("id") int id,
                                          @Param("gender") String gender,
                                          @Param("count") int count);
}

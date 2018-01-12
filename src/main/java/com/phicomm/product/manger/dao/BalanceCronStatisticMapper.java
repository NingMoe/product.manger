package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.statistic.BalanceAccountInfo;
import com.phicomm.product.manger.model.statistic.LocationCountBean;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    /**
     * 获取用户成员性别统计信息
     *
     * @return 用户成员性别信息
     */
    @MapKey("gender")
    Map<String, Map<String, Integer>> getBalanceMemberCountInfo();

    /**
     * 往用户性别信息表中插入数据
     *
     * @param id     表中id
     * @param gender 性别
     * @param count  数量
     */
    void insertUpdateBalanceMemberCountInfo(@Param("id") int id,
                                            @Param("gender") String gender,
                                            @Param("count") int count);

    /**
     * 获取体脂秤销售省份数据
     *
     * @return 省份数据信息
     */
    List<LocationCountBean> getBalanceSaleLocationCountInfo();

    /**
     * 插入各个省份体脂秤数量
     *
     * @param locationCountBeanList 体脂秤数量
     */
    void insertBalanceSaleLocationCountInfo(@Param("saleLocationCountList") List<LocationCountBean> locationCountBeanList);

    /**
     * 清空表balance_sale_location_count_each_hour中所有数据
     */
    void cleanBalanceSaleLocationCountInfo();
}

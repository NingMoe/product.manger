package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.statistic.BalanceMacStatus;
import com.phicomm.product.manger.model.statistic.CountBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 电子秤状态
 * Created by wei.yang on 2017/6/20.
 */
@Repository
public interface BalanceStatusMapper {

    /**
     * 上传数量
     *
     * @return 数量
     */
    List<CountBean> obtainCountByMonth(@Param("month") int month);

    /**
     * 上传数量
     *
     * @return 数量
     */
    List<CountBean> obtainCountByDay(@Param("day") int day);

    /**
     * 获取某一个电子秤的相关信息
     *
     * @param mac mac地址
     * @return 电子秤的相关信息
     */
    BalanceMacStatus obtainBalanceMacInfo(@Param("mac") String mac);

    /**
     * 根据mac地址获取第一次使用电子秤的时间
     *
     * @param mac mac地址
     * @return 第一次使用电子秤的时间
     */
    String obtainStatusCreateTime(@Param("mac") String mac);
}

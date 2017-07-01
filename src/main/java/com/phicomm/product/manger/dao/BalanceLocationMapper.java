package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.statistic.BalanceLocation;
import com.phicomm.product.manger.model.statistic.LocationCountBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 电子秤地区统计接口
 * <p>
 * Created by yufei.liu on 2017/6/25.
 */
@Repository
public interface BalanceLocationMapper {

    /**
     * 获取总的电子秤地区分布
     */
    List<BalanceLocation> getTotalBalanceLocation();

    /**
     * 上传数量
     *
     * @return 数量
     */
    List<LocationCountBean> obtainLocationCountByMonth(@Param("month") int month);

    /**
     * 上传数量
     *
     * @return 数量
     */
    List<LocationCountBean> obtainLocationCountByDay(@Param("day") int day);

    /**
     * 获取当天电子秤激活地区分布
     */
    List<BalanceLocation> getCurrentDateBalanceLocation(@Param("currentDate") String currentDate);

    /**
     * 获取电子秤销售数量
     */
    int getBalanceSaleNumber();
}

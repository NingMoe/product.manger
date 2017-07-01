package com.phicomm.product.manger.dao;

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
}

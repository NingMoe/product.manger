package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.statistic.CountBean;
import com.phicomm.product.manger.model.statistic.LocationCountBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 联璧激活
 * Created by wei.yang on 2017/6/14.
 */
@Repository
public interface LianbiActiveMapper {

    /**
     * 上传数量
     *
     * @return 数量
     */
    List<CountBean> obtainActiveCountByMonth(@Param("month") int month);

    /**
     * 上传数量
     *
     * @return 数量
     */
    List<CountBean> obtainActiveCountByDay(@Param("day") int day);

    /**
     * 上传数量
     *
     * @return 数量
     */
    List<LocationCountBean> obtainActiveLocationCountByMonth(@Param("month") int month);

    /**
     * 上传数量
     *
     * @return 数量
     */
    List<LocationCountBean> obtainActiveLocationCountByDay(@Param("day") int day);
}

package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.bpm.BpmCountBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 计算用户
 *
 * @author yafei.hou
 * @date 2017/11/9
 */
@Repository
public interface BpmSaleStatisticMapper {

    /**
     * 前N个月每个月的销售量
     *
     * @param months 最近n个月
     * @return List<BpmCountBean> 测量数据
     */
    List<BpmCountBean> obtainBpmSaleByMonth(@Param("months") int months);

    /**
     * 前N个天每天的销售量 最近n天
     * @param days 最近n个月
     * @return List<BpmCountBean> 测量数据
     */
    List<BpmCountBean> obtainBpmSaleNumByDay(@Param("days") int days);

    /**
     * 销售量 总量(含今天)
     * @return int 总量
     */
    int obtainBpmSaleNumAll();

    /**
     *
     * 今天的销售量
     * @return int 总量
     */
    int obtainBpmSaleNumToday();
}

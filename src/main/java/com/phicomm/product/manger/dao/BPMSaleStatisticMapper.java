package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.bpmstatistic.BPMCountBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 计算用户
 * Created by yafei.hou on 2017/11/9.
 */
@Repository
public interface BPMSaleStatisticMapper {

    /**
     * 前N个月每个月的销售量
     * @return
     */
    List<BPMCountBean> obtainBPMSaleNumByMonth(@Param("months") int months);

    /**
     * 前N个天每天的销售量 最近n天
     * @return
     */
    List<BPMCountBean> obtainBPMSaleNumByDay(@Param("days") int days);

    /**
     * 销售量 总量(含今天)
     * @return int 总量
     */
    int obtainBPMSaleNumAll();

    //今日销量
    int obtainBPMSaleNumToday();
}

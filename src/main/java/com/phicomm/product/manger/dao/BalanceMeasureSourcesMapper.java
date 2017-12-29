package com.phicomm.product.manger.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yafei.hou
 * Created by yafei.hou on 2017/12/27.
 */
@Repository
public interface BalanceMeasureSourcesMapper {


    /**
     * 最近N天，测量数据来源分类（balance_measure_info_X表）
     * @param tableNum 数据表编号
     * @param num 前n天
     * @param source 数据来源方式
     * @return 数据
     */
    List<Map<String, Object>>  getMeasureDataSources(@Param("tableNum")int tableNum,
                                                     @Param("num")int num,@Param("source") String source);

    /**
     * 某一天测量数据来源 （balance_measure_info_X表）
     * @param tableNum 数据表编号
     * @param date 日期
     * @return 数据
     */
    List<Map<String, Object>>  getMeasureDataSourcesOneDay(@Param("tableNum")int tableNum,
                                                           @Param("date")String date);


    /**
     * 最近N天，测量数据来源分类
     * @param num 前n天
     * @return 结果集合
     */
    List<Map<String, Object>>  getMeasureSources(@Param("num")int num);

    /**
     * 将每天的查询结果保存到新的数据库中
     * @param date 日期
     * @param autoClaim  自动认领的方式
     * @param claim 手动认领
     * @param locking 锁定上称
     */
    void insertMeasureSources(@Param("date")String date,
                              @Param("auto_claim") long autoClaim,
                              @Param("claim") long claim,
                              @Param("locking") long locking);

}

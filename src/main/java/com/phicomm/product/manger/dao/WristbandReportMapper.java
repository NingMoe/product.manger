package com.phicomm.product.manger.dao;

import com.phicomm.product.manger.model.statistic.CountBean;
import com.phicomm.product.manger.model.statistic.LocationCountBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * Created by xiang.zhang on 2018/2/26.
 * @author xiang.zhang
 */
@Repository
public interface WristbandReportMapper {

    /**
     * 插入联璧，万家两合作商手环手表的激活数据
     *
     * @param date 日期，格式为yyyy-mm-dd
     * @param num1 联璧激活数量
     * @param num2 万家金服激活数量
     * @param type
     */
    void addedActivationNum(@Param("date") String date,
                            @Param("lianbi") long num1,
                            @Param("wanjia") long num2,
                            @Param("type") String type);

    /**
     * 获取当前月份内联璧，万家的手环手表的每天激活数据量
     * @param type
     * @return 激活数据
     */
    List<Map<String, Object>> getActivationNumThisMonth(@Param("type") String type);


    /**
     * 更新月份统计表
     *
     * @param date 月份 格式yyyy-mm
     * @param num1 联璧当月总数
     * @param num2 万家金服当月激活总数
     * @param type
     */
    void upDateActivationMonthNum(@Param("month") String date,
                                  @Param("lianbi") long num1,
                                  @Param("wanjia") long num2,
                                  @Param("type") String type);


    /**
     * 获取累计新增K码激活量和联璧，万家两厂家新增K码激活总量
     *
     * @return 激活总量
     */
    List<Map<String, Object>> getActivationNum();


    /**
     * 获取最近N天的所有设备的激活情况统计（每个厂商激活数量）
     *暂时取最近30天
     * @param num 最近的天数
     * @return map数据表
     */
    List<Map<String, Object>> getActivationNumEveryDay(@Param("num") int num);

    /**
     * 获取最近N天W1的激活情况统计（每个厂商激活数量）
     *暂时取最近30天
     * @param num 最近的天数
     * @return map数据表
     */
    List<Map<String, Object>> getW1ActivationNumEveryDay(@Param("num") int num);

    /**
     * 获取最近N天W1P的激活情况统计（每个厂商激活数量）
     *暂时取最近30天
     * @param num 最近的天数
     * @return map数据表
     */
    List<Map<String, Object>> getW1PActivationNumEveryDay(@Param("num") int num);


    /**
     * 获取最近N天W2的激活情况统计（每个厂商激活数量）
     *暂时取最近30天
     * @param num 最近的天数
     * @return map数据表
     */
    List<Map<String, Object>> getW2ActivationNumEveryDay(@Param("num") int num);

    /**
     * 获取最近N天W2P的激活情况统计（每个厂商激活数量）
     *暂时取最近30天
     * @param num 最近的天数
     * @return map数据表
     */
    List<Map<String, Object>> getW2PActivationNumEveryDay(@Param("num") int num);



    /**
     * 获取某天联璧，万家激活状况
     *
     * @param date 当前日期 yyyy-mm-dd
     * @return 获取某天各个厂家激活状况
     */
    List<Map<String, Object>> getActivationStatisticDay(@Param("date") String date);


    /**
     * 获取最近N月的所有运动产品激活情况统计
     *暂时取最近10个月
     * @param month 最近n个月的情况
     * @return map数据表
     */
    List<Map<String, Object>> getActivationNumMonth(@Param("num") int month);


    /**
     * 获取最近N月的W1激活情况统计
     *暂时取最近10个月
     * @param month 最近n个月的情况
     * @return map数据表
     */
    List<Map<String, Object>> getW1ActivationNumMonth(@Param("num") int month);


    /**
     * 获取最近N月的W1P激活情况统计
     *暂时取最近10个月
     * @param month 最近n个月的情况
     * @return map数据表
     */
    List<Map<String, Object>> getW1PActivationNumMonth(@Param("num") int month);


    /**
     * 获取最近N月的W2激活情况统计
     *暂时取最近10个月
     * @param month 最近n个月的情况
     * @return map数据表
     */
    List<Map<String, Object>> getW2ActivationNumMonth(@Param("num") int month);

    /**
     * 获取最近N月的W2P激活情况统计
     *暂时取最近10个月
     * @param month 最近n个月的情况
     * @return map数据表
     */
    List<Map<String, Object>> getW2PActivationNumMonth(@Param("num") int month);


    /**
     * 上传数量
     * @param month 月
     * @param type
     * @return 数量
     */
    List<CountBean> getNumByMonth(@Param("month") int month,
                                  @Param("type") String type);


    /**
     * 上传数量
     * @param month 月
     * @return 数量
     */
    List<CountBean> getTotalNumByMonth(@Param("month") int month);



    /**
     * 上传数量
     * @param day 天
     * @return 数量
     */
    List<CountBean> getNumByDay(@Param("day") int day);


    /**
     * 上传数量
     * @param day
     * @param pageSize
     * @param type
     * @return 数量
     */
    List<LocationCountBean> getLocationNumByDay(@Param("day") int day,
                                                @Param("type")String type,
                                                @Param("pageSize") int pageSize);


    /**
     * 不分设备类型
     * @param day
     * @param pageSize
     * @return
     */
    List<LocationCountBean> getTotalLocationNumByDay(@Param("day") int day,
                                                     @Param("pageSize") int pageSize);


    /**
     * 最近N天每天激活总量
     *
     * @param num 最近的天数
     * @return map数据表
     */
    List<Map<String, Object>> getActivationStatisticByDay(@Param("num") int num);

}

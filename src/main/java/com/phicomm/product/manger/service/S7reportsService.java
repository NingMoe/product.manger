package com.phicomm.product.manger.service;

import com.phicomm.product.manger.dao.S7reportsMapper;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author yafei.hou
 *         Created by yafei.hou on 2017/12/26.
 */
@Service
public class S7reportsService {

    private static final Logger logger = Logger.getLogger(S7reportsService.class);

    private static final String LIANBI_KEY = "lianbi";
    private static final String WANJIA_KEY = "wanjia";
    private static final String DATE_KEY = "date";
    private static final int LESTEST_DAYS = 30;
    private static final int LESTEST_MONTH = 10;

    private S7reportsMapper s7reportsMapper;

    @Autowired
    public S7reportsService(S7reportsMapper s7reportsMapper) {
        this.s7reportsMapper = s7reportsMapper;
        Assert.notNull(this.s7reportsMapper);
    }

    /**
     * 插入各个合作商S7的激活数据,并且将数据更新到月份统计表中
     *
     * @param date   日期，格式为yyyy-MM-dd
     * @param lianbi 联璧的激活数量
     * @param wanjia 万家金服的激活数量
     */
    public void insertActivationData(String date, long lianbi, long wanjia) throws Exception {
        if (date == null) {
            throw new Exception("need the exacted date");
        }
        CustomerContextHolder.selectLocalDataSource();
        s7reportsMapper.addedActivationCount(date, lianbi, wanjia);
        CustomerContextHolder.clearDataSource();
        try {
            updateActivationMonthCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateActivationMonthCount() throws Exception {
        CustomerContextHolder.selectLocalDataSource();
        List<? extends Map<String, Object>> list = s7reportsMapper.obtainActivationStatisticThisMonth();
        if (list.isEmpty()) {
            throw new Exception("update data failed");
        }
        Map<String, Object> map = list.get(0);
        s7reportsMapper.upDateActivationMonthCount((String) map.get(DATE_KEY),
                Long.parseLong(map.get(LIANBI_KEY).toString()), Long.parseLong(map.get(WANJIA_KEY).toString()));
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 添加某一天的用户活跃数
     *
     * @param date    日期
     * @param ios     活跃用户
     * @param android 活跃用户
     * @throws Exception 日期字符串异常
     */
    public void insertActiveUsersCount(String date, long ios, long android) throws Exception {
        if (date == null) {
            throw new Exception("need the exacted date");
        }
        CustomerContextHolder.selectLocalDataSource();
        s7reportsMapper.addedActiveUsersCount(date, ios, android);
        CustomerContextHolder.clearDataSource();
    }

    /**
     * 获取最近N天的激活情况统计（每个厂商激活数量）
     * @return List\<Map\<String, Object>>
     */
    public List<Map<String, Object>> obtainActivationStatisticEveryDay(){
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = s7reportsMapper.obtainActivationStatisticEveryDay(LESTEST_DAYS);
        CustomerContextHolder.clearDataSource();
        logger.warn(list.toString());
        return list;
    }

    /**
     * 最近N天每天激活总量
     * @param days 天数
     * @return 每天激活总量
     */
    public List<Map<String, Object>> obtainActivationStatisticByDay(int days){
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = s7reportsMapper.obtainActivationStatisticByDay(days);
        logger.warn(list.toString());
        CustomerContextHolder.clearDataSource();
        return list;
    }

    /**
     * 获取某天各个厂家激活状况
     * @param date 日期 yyyy-mm-dd
     * @return 数据列表
     */
    public List<Map<String, Object>> obtainActivationStatisticDay(String date){
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = s7reportsMapper.obtainActivationStatisticDay(date);
        logger.warn(list.toString());
        CustomerContextHolder.clearDataSource();
        return list;
    }

    /**
     * 获取最近N月的激活情况统计
     * @return 数据列表
     */
    public List<Map<String, Object>> obtainActivationStatisticMonth(){
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = s7reportsMapper.obtainActivationStatisticMonth(LESTEST_MONTH);
        logger.warn(list.toString());
        CustomerContextHolder.clearDataSource();
        return list;
    }

    /**
     * 获取每个厂家激活总量
     * @return 数据列表
     */
    public List<Map<String, Object>> obtainActivationAllCounts(){
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = s7reportsMapper.obtainActivationAllCounts();
        CustomerContextHolder.clearDataSource();
        logger.warn(list);
        return list;
    }

    /**
     * 获取某天各个设备的用户活跃量
     * @param  date 日期 格式为yyyy-mm-dd
     * @return 数据列表
     */
    public List<Map<String, Object>> activeUsersCount(String date) throws Exception {
        if (date == null) {
            throw new Exception("need the exact date like yyyy-mm-dd");
        }
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = s7reportsMapper.activeUsersCount(date);
        CustomerContextHolder.clearDataSource();
        logger.warn(list);
        return list;
    }

    /**
     * 获取最近N天用户活跃度
     * @return 数据列表
     */
    public List<Map<String, Object>> activeUsersCountEveryDay() throws Exception {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = s7reportsMapper.activeUsersCountEveryDay(LESTEST_DAYS);
        CustomerContextHolder.clearDataSource();
        logger.warn(list);
        return list;
    }

}

package com.phicomm.product.manger.service;

import com.google.common.collect.Lists;
import com.phicomm.product.manger.dao.WristbandReportMapper;
import com.phicomm.product.manger.model.statistic.CountBean;
import com.phicomm.product.manger.model.statistic.LocationCountBean;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by xiang.zhang on 2018/2/26.
 * @author xiang.zhang
 */
@Service
public class WristbandReportService {

    private static final Logger logger = Logger.getLogger(S7reportsService.class);

    private WristbandReportMapper wristbandReportMapper;

    private static final int RECENT_DAYS = 30;

    private static final int RECENT_MONTH = 10;

    private static final String DATE_KEY = "date";

    private static final String LIANBI_KEY = "lianbi";

    private static final String WANJIA_KEY = "wanjia";

    private static final String DEVICE_TYPE = "type";

    private static final int DEFAULT_LOCATION_PAGE_SIZE = 15;

    private static final int DEFAULT_DAY = 30;

    @Autowired
    public WristbandReportService(WristbandReportMapper wristbandReportMapper){

        this.wristbandReportMapper = wristbandReportMapper;
        Assert.notNull(this.wristbandReportMapper);
    }

    /**
     * 插入联璧，万家两合作商手环手表的激活数据,并且将数据更新到月份统计表中
     *
     * @param date   日期，格式为yyyy-MM-dd
     * @param lianbi 联璧的激活数量
     * @param wanjia 万家金服的激活数量
     */
    public void insertActivationData(String date, long lianbi, long wanjia,String type) throws Exception {
        if (date == null) {
            throw new Exception("need the exacted date");
        }
        CustomerContextHolder.selectLocalDataSource();
        wristbandReportMapper.addedActivationNum(date, lianbi, wanjia,type);
        CustomerContextHolder.clearDataSource();
        try {
            updateActivationMonthNum(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新月份统计表中的数据
     */
    private void updateActivationMonthNum(String type) throws Exception {
        CustomerContextHolder.selectLocalDataSource();
        List<? extends Map<String, Object>> list = wristbandReportMapper.getActivationNumThisMonth(type);
        if (list.isEmpty()) {
            throw new Exception("update data failed");
        }
        logger.info(list);
        Map<String, Object> map = list.get(0);
        wristbandReportMapper.upDateActivationMonthNum((String) map.get(DATE_KEY),
                Long.parseLong(map.get(LIANBI_KEY).toString()), Long.parseLong(map.get(WANJIA_KEY).toString()),(String) map.get(DEVICE_TYPE));
        CustomerContextHolder.clearDataSource();
    }



    /**
     * 获取累计新增K码激活量和联璧，万家两厂家新增K码激活总量
     *
     * @return 数据列表
     */
    public List<Map<String, Object>> getActivationNum() {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = wristbandReportMapper.getActivationNum();
        CustomerContextHolder.clearDataSource();
        logger.warn(list);
        return list;
    }

    /**
     * 获取最近N天所有类型设备的激活情况统计（每个厂商激活数量）
     * 暂时取最近30天
     * @return List\<Map\<String, Object>>
     */
    public List<Map<String, Object>> getActivationNumEveryDay() {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = wristbandReportMapper.getActivationNumEveryDay(RECENT_DAYS);
        CustomerContextHolder.clearDataSource();
        logger.warn(list.toString());
        return list;
    }


    /**
     * 获取最近N天W1的激活情况统计（每个厂商激活数量）
     * 暂时取最近30天
     * @return List\<Map\<String, Object>>
     */
    public List<Map<String, Object>> getW1ActivationNumEveryDay() {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = wristbandReportMapper.getW1ActivationNumEveryDay(RECENT_DAYS);
        CustomerContextHolder.clearDataSource();
        logger.warn(list.toString());
        return list;
    }

    /**
     * 获取最近N天W1P的激活情况统计（每个厂商激活数量）
     * 暂时取最近30天
     * @return List\<Map\<String, Object>>
     */
    public List<Map<String, Object>> getW1PActivationNumEveryDay() {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = wristbandReportMapper.getW1PActivationNumEveryDay(RECENT_DAYS);
        CustomerContextHolder.clearDataSource();
        logger.warn(list.toString());
        return list;
    }

    /**
     * 获取最近N天W2的激活情况统计（每个厂商激活数量）
     * 暂时取最近30天
     * @return List\<Map\<String, Object>>
     */
    public List<Map<String, Object>> getW2ActivationNumEveryDay() {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = wristbandReportMapper.getW2ActivationNumEveryDay(RECENT_DAYS);
        CustomerContextHolder.clearDataSource();
        logger.warn(list.toString());
        return list;
    }

    /**
     * 获取最近N天W2P的激活情况统计（每个厂商激活数量）
     * 暂时取最近30天
     * @return List\<Map\<String, Object>>
     */
    public List<Map<String, Object>> getW2PActivationNumEveryDay() {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = wristbandReportMapper.getW2PActivationNumEveryDay(RECENT_DAYS);
        CustomerContextHolder.clearDataSource();
        logger.warn(list.toString());
        return list;
    }

    /**
     * 获取某天各个厂家激活状况
     *
     * @param date 日期 yyyy-mm-dd
     * @return 数据列表
     */
    public List<Map<String, Object>> getActivationStatisticDay(String date) {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = wristbandReportMapper.getActivationStatisticDay(date);
        logger.warn(list.toString());
        CustomerContextHolder.clearDataSource();
        return list;
    }


    /**
     * 获取最近N月的手环手表激活情况统计
     * 暂时取最近10个月
     * @return 数据列表
     */
    public List<Map<String, Object>> getActivationNumMonth() {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = wristbandReportMapper.getActivationNumMonth(RECENT_MONTH);
        logger.warn(list.toString());
        CustomerContextHolder.clearDataSource();
        return list;
    }

    /**
     * 获取最近N月的W1激活情况统计
     * 暂时取最近10个月
     * @return 数据列表
     */
    public List<Map<String, Object>> getW1ActivationNumMonth() {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = wristbandReportMapper.getW1ActivationNumMonth(RECENT_MONTH);
        logger.warn(list.toString());
        CustomerContextHolder.clearDataSource();
        return list;
    }

    /**
     * 获取最近N月的W1P激活情况统计
     * 暂时取最近10个月
     * @return 数据列表
     */
    public List<Map<String, Object>> getW1PActivationNumMonth() {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = wristbandReportMapper.getW1PActivationNumMonth(RECENT_MONTH);
        logger.warn(list.toString());
        CustomerContextHolder.clearDataSource();
        return list;
    }
    /**
     * 获取最近N月的W2激活情况统计
     * 暂时取最近10个月
     * @return 数据列表
     */
    public List<Map<String, Object>> getW2ActivationNumMonth() {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = wristbandReportMapper.getW2ActivationNumMonth(RECENT_MONTH);
        logger.warn(list.toString());
        CustomerContextHolder.clearDataSource();
        return list;
    }

    /**
     * 获取最近N月的W2P激活情况统计
     * 暂时取最近10个月
     * @return 数据列表
     */
    public List<Map<String, Object>> getW2PActivationNumMonth() {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = wristbandReportMapper.getW2PActivationNumMonth(RECENT_MONTH);
        logger.warn(list.toString());
        CustomerContextHolder.clearDataSource();
        return list;
    }

    /**
     * 每个月的上传量:不包含今天
     *
     * @return 上传量
     */
    public Map<String, Integer> getNumByMonth(int month, String type) {
        DateTime dateTime = new DateTime(DateTime.now()).minusDays(1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        List<CountBean> countBeans = Lists.newArrayList();
        CustomerContextHolder.selectProdDataSource();

        countBeans = wristbandReportMapper.getNumByMonth(month,type);

        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> result = new TreeMap<>();
        for (CountBean countBean : countBeans) {
            result.put(countBean.getGenerateTime(), countBean.getGenerateCount());
        }
        for (int i = 0; i < month; i++) {
            String generateTime = dateFormat.format(dateTime.toDate());
            if (!result.containsKey(generateTime)) {
                result.put(generateTime, 0);
            }
            dateTime = dateTime.minusMonths(1);
        }

        return result;
    }

    /**
     * 每个月的上传量:不包含今天
     *
     * @return 上传量
     */
    public Map<String, Integer> getTotalNumByMonth(int month) {
        DateTime dateTime = new DateTime(DateTime.now()).minusDays(1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        List<CountBean> countBeans = Lists.newArrayList();
        CustomerContextHolder.selectProdDataSource();
        countBeans = wristbandReportMapper.getTotalNumByMonth(month);

        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> result = new TreeMap<>();
        for (CountBean countBean : countBeans) {
            result.put(countBean.getGenerateTime(), countBean.getGenerateCount());
        }
        for (int i = 0; i < month; i++) {
            String generateTime = dateFormat.format(dateTime.toDate());
            if (!result.containsKey(generateTime)) {
                result.put(generateTime, 0);
            }
            dateTime = dateTime.minusMonths(1);
        }

        return result;
    }


    /**
     * 获取最近30天每天的上传量
     *
     * @return 上传量
     */
    public Map<String, Integer> getNumByDay(int day, String type) {
        DateTime dateTime = new DateTime(DateTime.now()).minusDays(1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
        List<CountBean> countBeans = Lists.newArrayList();
        CustomerContextHolder.selectProdDataSource();
        countBeans = wristbandReportMapper.getNumByDay(day);
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, Integer> result = new TreeMap<>();
        for (CountBean countBean : countBeans) {
            result.put(countBean.getGenerateTime(), countBean.getGenerateCount());
        }
        for (int i = 0; i < day; i++) {
            String generateTime = dateFormat.format(dateTime.toDate());
            if (!result.containsKey(generateTime)) {
                result.put(generateTime, 0);
            }
            dateTime = dateTime.minusDays(1);
        }

        return result;
    }


    /**
     * 获取N天的位置信息
     */
    public Map<String, Integer> getLocationNumByDay(int day, String type, int pageSize) {
        List<LocationCountBean> countBeans = Lists.newArrayList();
        day = day <= 0 ? DEFAULT_DAY : day;
        pageSize = pageSize <= 0 ? DEFAULT_LOCATION_PAGE_SIZE : pageSize;
        CustomerContextHolder.selectProdDataSource();
        countBeans = wristbandReportMapper.getLocationNumByDay(day,type,pageSize);
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> result = new LinkedHashMap<>();
        for (LocationCountBean countBean : countBeans) {
            result.put(countBean.getProvince(), countBean.getGenerateCount());
        }
        return result;
    }



    /**
     * 获取N天的位置信息
     * 不分设备类型
     */
    public Map<String, Integer> getTotalLocationNumByDay(int day, int pageSize) {
        List<LocationCountBean> countBeans = Lists.newArrayList();
        day = day <= 0 ? DEFAULT_DAY : day;
        pageSize = pageSize <= 0 ? DEFAULT_LOCATION_PAGE_SIZE : pageSize;
        CustomerContextHolder.selectProdDataSource();
        countBeans = wristbandReportMapper.getTotalLocationNumByDay(day,pageSize);
        CustomerContextHolder.clearDataSource();
        if (countBeans.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> result = new LinkedHashMap<>();
        for (LocationCountBean countBean : countBeans) {
            result.put(countBean.getProvince(), countBean.getGenerateCount());
        }
        return result;
    }


    /**
     * 最近N天每天激活总量
     *
     * @param days 天数
     * @return 每天激活总量
     */
    public List<Map<String, Object>> getActivationStatisticByDay(int days) {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = wristbandReportMapper.getActivationStatisticByDay(days);
        logger.warn(list.toString());
        CustomerContextHolder.clearDataSource();
        return list;
    }

}

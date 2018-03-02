package com.phicomm.product.manger.service;

import com.phicomm.product.manger.dao.WristbandReportMapper;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;


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
    public void insertActivationData(String date, long lianbi, long wanjia) throws Exception {
        if (date == null) {
            throw new Exception("need the exacted date");
        }
        CustomerContextHolder.selectLocalDataSource();
        wristbandReportMapper.addedActivationNum(date, lianbi, wanjia);
        CustomerContextHolder.clearDataSource();
        try {
            updateActivationMonthNum();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新月份统计表中的数据
     */
    private void updateActivationMonthNum() throws Exception {
        CustomerContextHolder.selectLocalDataSource();
        List<? extends Map<String, Object>> list = wristbandReportMapper.getActivationNumThisMonth();
        if (list.isEmpty()) {
            throw new Exception("update data failed");
        }
        logger.info(list);
        Map<String, Object> map = list.get(0);
        wristbandReportMapper.upDateActivationMonthNum((String) map.get(DATE_KEY),
                Long.parseLong(map.get(LIANBI_KEY).toString()), Long.parseLong(map.get(WANJIA_KEY).toString()));
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
     * 获取最近N天手环手表的激活情况统计（每个厂商激活数量）
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

}

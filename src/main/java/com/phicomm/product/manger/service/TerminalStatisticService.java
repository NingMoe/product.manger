package com.phicomm.product.manger.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.phicomm.product.manger.dao.TerminalStatisticMapper;
import com.phicomm.product.manger.enumeration.TerminalDataTypeEnum;
import com.phicomm.product.manger.model.terminal.PageWithPlatformEntity;
import com.phicomm.product.manger.model.terminal.PeriodWithPlatformEntity;
import com.phicomm.product.manger.model.terminal.StatisticEntity;
import com.phicomm.product.manger.model.terminal.TerminalCommonEntity;
import com.phicomm.product.manger.module.terminal.impl.TerminalMongoQueryImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 设备分析
 *
 * @author wei.yang on 2017/12/28
 */
@Service
public class TerminalStatisticService {

    private static final Logger logger = Logger.getLogger(TerminalStatisticService.class);

    private TerminalStatisticMapper terminalStatisticMapper;

    private TerminalMongoQueryImpl mongoQuery;

    @Autowired
    public TerminalStatisticService(TerminalStatisticMapper terminalStatisticMapper,
                                    TerminalMongoQueryImpl mongoQuery) {
        this.terminalStatisticMapper = terminalStatisticMapper;
        this.mongoQuery = mongoQuery;
        Assert.notNull(this.terminalStatisticMapper);
        Assert.notNull(this.mongoQuery);
    }

    /**
     * 获取设备信息
     *
     * @return 统计信息
     */
    public List<StatisticEntity> obtainPeriodStatisticDetail(PeriodWithPlatformEntity periodModel) {
        List<StatisticEntity> statisticEntities = terminalStatisticMapper.getTimeStatisticInfo(periodModel);
        if (statisticEntities == null || statisticEntities.isEmpty()) {
            return Lists.newLinkedList();
        }
        return statisticEntities;
    }

    /**
     * 获取设备信息
     *
     * @return 统计信息
     */
    public List<StatisticEntity> obtainPageStatisticDetail(PageWithPlatformEntity pageEntity) {
        List<StatisticEntity> statisticEntities = terminalStatisticMapper.getPageStatisticInfo(pageEntity);
        if (statisticEntities == null || statisticEntities.isEmpty()) {
            return Lists.newLinkedList();
        }
        return statisticEntities;
    }

    /**
     * 数据同步：同步昨天一天的数据
     */
    public void syncAllData() {
        for (TerminalDataTypeEnum dataTypeEnum : TerminalDataTypeEnum.values()) {
            List<TerminalCommonEntity> terminalCommonEntities = mongoQuery.historyGroup(dataTypeEnum.getMongoKey());
            logger.info(JSONObject.toJSONString(terminalCommonEntities));
            if (terminalCommonEntities == null || terminalCommonEntities.isEmpty()) {
                continue;
            }
            terminalStatisticMapper.groupInsert(terminalCommonEntities, dataTypeEnum.getDataType());
        }
    }

    /**
     * 数据同步：同步昨天一天的数据
     */
    public void syncYesterdayData() {
        for (TerminalDataTypeEnum dataTypeEnum : TerminalDataTypeEnum.values()) {
            List<TerminalCommonEntity> terminalCommonEntities = mongoQuery.yesterdayGroup(dataTypeEnum.getMongoKey());
            terminalStatisticMapper.groupInsert(terminalCommonEntities, dataTypeEnum.getDataType());
        }
    }
}

package com.phicomm.product.manger.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.phicomm.product.manger.dao.TerminalStatisticMapper;
import com.phicomm.product.manger.enumeration.PlatformEnum;
import com.phicomm.product.manger.enumeration.TerminalDataTypeEnum;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.exception.PlatformNotExistException;
import com.phicomm.product.manger.exception.TerminalStatisticTypeNotSupportException;
import com.phicomm.product.manger.model.terminal.*;
import com.phicomm.product.manger.module.terminal.impl.TerminalMongoQueryImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
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


    public List<HistoryResultEntity> obtainHistoryData(SearchWithCertainTimeEntity certainTimeEntity){
        return terminalStatisticMapper.obtainCompareData(certainTimeEntity);
    }

    /**
     * 获取设备信息:先不限定可查询的最大时间段，也先不补充当某天没数据时返回对应为0
     *
     * @param periodModel 查询信息
     * @return 统计信息
     * @throws TerminalStatisticTypeNotSupportException 设备类型不支持
     * @throws PlatformNotExistException                平台不支持
     * @throws DataFormatException                      数据格式错误
     */
    public List<StatisticEntity> obtainPeriodStatisticDetail(PeriodWithPlatformEntity periodModel)
            throws TerminalStatisticTypeNotSupportException, PlatformNotExistException, DataFormatException {
        checkTimePeriodRequest(periodModel);
        List<StatisticEntity> statisticEntities = terminalStatisticMapper.getTimeStatisticInfo(periodModel);
        if (statisticEntities == null || statisticEntities.isEmpty()) {
            return Lists.newLinkedList();
        }
        return statisticEntities;
    }

    /**
     * 获取设备信息
     *
     * @param pageEntity 页面信息
     * @return 统计数据
     * @throws TerminalStatisticTypeNotSupportException 分析类型不支持
     * @throws PlatformNotExistException                平台不支持
     * @throws DataFormatException                      数据格式异常
     */
    public List<StatisticEntity> obtainPageStatisticDetail(PageWithPlatformEntity pageEntity)
            throws TerminalStatisticTypeNotSupportException, PlatformNotExistException, DataFormatException {
        checkPageDataRequest(pageEntity);
        int begin = pageEntity.getPageNumber() * pageEntity.getPageSize();
        List<StatisticEntity> statisticEntities = terminalStatisticMapper.getPageStatisticInfo(pageEntity, begin);
        if (statisticEntities == null || statisticEntities.isEmpty()) {
            return Lists.newLinkedList();
        }
        return statisticEntities;
    }

    /**
     * 数据同步：同步数据
     */
    public void syncAllData() {
        for (TerminalDataTypeEnum dataTypeEnum : TerminalDataTypeEnum.values()) {
            List<TerminalCommonEntity> terminalCommonEntities = mongoQuery.historyGroup(dataTypeEnum.getMongoKey());
            if (terminalCommonEntities == null || terminalCommonEntities.isEmpty()) {
                continue;
            }
            logger.info(JSONObject.toJSONString(terminalCommonEntities));
            terminalStatisticMapper.groupInsert(terminalCommonEntities, dataTypeEnum.getDataType());
        }
    }

    /**
     * 数据同步：同步昨天一天的数据
     */
    public void syncYesterdayData() {
        for (TerminalDataTypeEnum dataTypeEnum : TerminalDataTypeEnum.values()) {
            List<TerminalCommonEntity> terminalCommonEntities = mongoQuery.yesterdayGroup(dataTypeEnum.getMongoKey());
            if (terminalCommonEntities == null || terminalCommonEntities.isEmpty()) {
                continue;
            }
            logger.info(JSONObject.toJSONString(terminalCommonEntities));
            terminalStatisticMapper.groupInsert(terminalCommonEntities, dataTypeEnum.getDataType());
        }
    }

    /**
     * 核对页面数据
     *
     * @param pageEntity 页面信息
     * @throws DataFormatException                      数据格式异常
     * @throws PlatformNotExistException                平台不支持
     * @throws TerminalStatisticTypeNotSupportException 分析类型不支持
     */
    private void checkPageDataRequest(PageWithPlatformEntity pageEntity) throws DataFormatException,
            PlatformNotExistException, TerminalStatisticTypeNotSupportException {
        if (pageEntity == null) {
            throw new DataFormatException();
        }
        Integer pageSize = pageEntity.getPageSize();
        Integer pageNumber = pageEntity.getPageNumber();
        boolean invalid = pageNumber == null || pageSize == null || pageSize <= 0 || pageNumber < 0;
        if (invalid) {
            throw new DataFormatException();
        }
        String platform = pageEntity.getPlatform();
        if (!PlatformEnum.exist(platform)) {
            throw new PlatformNotExistException();
        }
        String type = pageEntity.getType();
        if (!TerminalDataTypeEnum.exist(type)) {
            throw new TerminalStatisticTypeNotSupportException();
        }
    }

    /**
     * 按时间段来获取统计数据
     *
     * @param periodModel 时间段信息
     * @throws PlatformNotExistException                平台不支持
     * @throws TerminalStatisticTypeNotSupportException 分析类型不支持
     * @throws DataFormatException                      数据格式错误
     */
    private void checkTimePeriodRequest(PeriodWithPlatformEntity periodModel)
            throws PlatformNotExistException, TerminalStatisticTypeNotSupportException, DataFormatException {
        if (periodModel == null) {
            throw new DataFormatException();
        }
        Date startTime = periodModel.getStartTime();
        Date endTime = periodModel.getEndTime();
        boolean valid = startTime != null && endTime != null && endTime.after(startTime);
        if (!valid) {
            throw new DataFormatException();
        }
        String platform = periodModel.getPlatform();
        if (!PlatformEnum.exist(platform)) {
            throw new PlatformNotExistException();
        }
        String type = periodModel.getType();
        if (!TerminalDataTypeEnum.exist(type)) {
            throw new TerminalStatisticTypeNotSupportException();
        }
    }
}

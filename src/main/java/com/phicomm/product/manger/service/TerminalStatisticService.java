package com.phicomm.product.manger.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * 获取统计数据
     *
     * @param certainTimeEntity 一般只获取昨天的数据
     * @return 统计数据
     */
    public List<HistoryResultEntity> obtainHistoryData(SearchWithCertainTimeEntity certainTimeEntity)
            throws DataFormatException {
        checkCertainTimeData(certainTimeEntity);
        List<HistoryResultEntity> result = terminalStatisticMapper.obtainCompareData(certainTimeEntity);
        if (result == null || result.isEmpty()) {
            return Lists.newLinkedList();
        }
        return terminalStatisticMapper.obtainCompareData(certainTimeEntity);
    }

    /**
     * 获取某一时间段、某个平台、某个类型数据的线图数据
     *
     * @param periodWithPlatformEntity 时间段及平台、类型数据
     * @return 格式化好的数据
     * @throws TerminalStatisticTypeNotSupportException 类型不支持
     * @throws PlatformNotExistException                平台不支持
     * @throws DataFormatException                      数据格式错误
     */
    public List<Map<String, Object>> obtainLineChartData(PeriodWithPlatformEntity periodWithPlatformEntity)
            throws TerminalStatisticTypeNotSupportException, PlatformNotExistException, DataFormatException {
        checkTimePeriodRequest(periodWithPlatformEntity);
        List<ResultWithDataTime> monthDatas = terminalStatisticMapper.obtainTerminalLineChartData(periodWithPlatformEntity);
        return formatData(monthDatas, periodWithPlatformEntity);
    }

    /**
     * 这一块不考虑返回横轴坐标，只返回对应对象对应的y轴数据
     *
     * @param dataTimes 待格式化的数据
     * @param entity    起始时间
     * @return 格式化好的时间
     */
    private List<Map<String, Object>> formatData(List<ResultWithDataTime> dataTimes, PeriodWithPlatformEntity entity) {
        if (dataTimes == null || dataTimes.isEmpty()) {
            return Lists.newLinkedList();
        }
        Map<String, List<ResultWithDataTime>> result = dataTimes.stream()
                .collect(Collectors.groupingBy(ResultWithDataTime::getCompareObject));
        Date startTime = entity.getStartTime();
        Date endTime = entity.getEndTime();
        List<Map<String, Object>> resultList = Lists.newArrayList();
        result.forEach((s, resultWithDataTimes) -> {
            Map<String, Object> item = Maps.newHashMap();
            List<Integer> chartData = getChartData(resultWithDataTimes, startTime, endTime);
            item.put("data", chartData);
            item.put("name", s);
            resultList.add(item);
        });
        logger.info(resultList);
        return resultList;
    }

    /**
     * 单组数据数据补充
     *
     * @param resultWithDataTimes 数据
     * @param startTime           开始时间
     * @param endTime             终止时间
     * @return 格式好的数据
     */
    private List<Integer> getChartData(List<ResultWithDataTime> resultWithDataTimes, Date startTime, Date endTime) {
        List<Integer> result = Lists.newArrayList();
        ZoneId zoneId = ZoneId.of("UTC+8");
        Instant startInstant = startTime.toInstant();
        Instant endInstant = endTime.toInstant();
        LocalDateTime start = LocalDateTime.ofInstant(startInstant, zoneId);
        LocalDateTime end = LocalDateTime.ofInstant(endInstant, zoneId);
        Map<String, Integer> timeMap = Maps.newHashMap();
        resultWithDataTimes.forEach(resultWithDataTime ->
                timeMap.put(resultWithDataTime.getDataTime(), resultWithDataTime.getCount()));
        while (!start.isAfter(end)) {
            Integer count = timeMap.get(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            count = count == null ? 0 : count;
            result.add(count);
            start = start.plusDays(1);
        }
        return result;
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
     * 数据同步：同步数据
     */
    public void syncAllDataV2() {
        for (TerminalDataTypeEnum dataTypeEnum : TerminalDataTypeEnum.values()) {
            List<TerminalCommonEntity> terminalCommonEntities = mongoQuery.historyKeyGroup(dataTypeEnum.getMongoKey());
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
     * 核对参数格式
     *
     * @param certainTimeEntity 指定时间的查询
     * @throws DataFormatException 数据格式异常
     */
    private void checkCertainTimeData(SearchWithCertainTimeEntity certainTimeEntity) throws DataFormatException {
        if (certainTimeEntity == null) {
            throw new DataFormatException();
        }
        String platform = certainTimeEntity.getPlatform();
        String dateType = certainTimeEntity.getDateType();
        if (!PlatformEnum.exist(platform) || !TerminalDataTypeEnum.exist(dateType)) {
            throw new DataFormatException();
        }
        String searchTime = certainTimeEntity.getSearchTime();
        try {
            LocalDate time = LocalDate.parse(searchTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (time.isAfter(LocalDate.now())) {
                throw new DataFormatException();
            }
        } catch (Exception e) {
            throw new DataFormatException();
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
        String type = pageEntity.getDateType();
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
        String type = periodModel.getDateType();
        if (!TerminalDataTypeEnum.exist(type)) {
            throw new TerminalStatisticTypeNotSupportException();
        }
    }
}

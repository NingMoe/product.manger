package com.phicomm.product.manger.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.phicomm.product.manger.dao.BalanceMeasureSourcesMapper;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yafei.hou
 *         Created by yafei.hou on 2017/12/27.
 */
@Service
public class BalanceMeasureSourceService {

    private static final Logger log = Logger.getLogger(BalanceMeasureSourceService.class);

    private static final int LATEST_DAY = 10;
    private static final String AUTO_CLAIM = "auto_claim";
    private static final String CLAIM = "claim";
    private static final String LOCKING = "locking";
    private static final String SOURCE = "source";
    private static final String SOURCE_KEY = SOURCE;
    private static final String COUNTS_KEY = "counts";
    private BalanceMeasureSourcesMapper balanceMeasureSourcesMapper;

    @Autowired
    public BalanceMeasureSourceService(BalanceMeasureSourcesMapper balanceMeasureSourcesMapper) {
        this.balanceMeasureSourcesMapper = balanceMeasureSourcesMapper;
        Assert.notNull(this.balanceMeasureSourcesMapper);
    }

    /**
     * 定时任务
     */
    public void updateSourceStatistic() {
        Map<String, Long> mapAuto = obtainDataCounts(AUTO_CLAIM);
        Map<String, Long> mapClaim = obtainDataCounts(CLAIM);
        Map<String, Long> mapLocking = obtainDataCounts(LOCKING);
        CustomerContextHolder.selectLocalDataSource();
        Set<String> sets= Sets.newHashSet();
        sets.addAll(mapAuto.keySet());
        sets.addAll(mapClaim.keySet());
        sets.addAll(mapLocking.keySet());
        for (String key : sets) {
            long auto = mapAuto.getOrDefault(key, 0L);
            long claim = mapClaim.getOrDefault(key,0L);
            long locking = mapLocking.getOrDefault(key, 0L);
            balanceMeasureSourcesMapper.insertMeasureSources(key, auto, claim, locking);
        }
        CustomerContextHolder.clearDataSource();

    }

    private Map<String, Long> obtainDataCounts(String key) {
        Map<String, Long> maps = Maps.newHashMap();
        CustomerContextHolder.selectProdDataSource();
        for (int i = 0; i < 50; i++) {
            List<? extends Map<String, Object>> result = balanceMeasureSourcesMapper.getMeasureDataSources(i, LATEST_DAY, key);
            for (Map<String, Object> mapData : result) {
                String keys = (String) mapData.get("date");
                if (maps.containsKey(keys)) {
                    maps.put(keys, maps.get(keys) + Long.parseLong(mapData.get("counts").toString()));
                } else {
                    maps.put(keys, Long.parseLong(mapData.get("counts").toString()));
                }
            }
        }
        log.info("------------------------------------------------------------");
        log.warn(maps);
        CustomerContextHolder.clearDataSource();
        return maps;
    }

    /**
     * 最近N天，测量数据来源分类
     */
    public List<Map<String, Object>> getMeasureSources() {
        CustomerContextHolder.selectLocalDataSource();
        List<Map<String, Object>> list = balanceMeasureSourcesMapper.getMeasureSources(LATEST_DAY);
        CustomerContextHolder.clearDataSource();
        return list;
    }

    /**
     * 获取某天的统计结果
     * @param date 日期
     * @return 结果
     * @throws Exception 异常
     */
    public Map<String, Long> getMeasureDataSourcesOneDay(String date) throws Exception {
        if (date == null) {
            throw new Exception("need the exact date");
        }
        CustomerContextHolder.selectProdDataSource();
        Map<String, Long> maps = Maps.newHashMap();
        for (int i = 0; i < 50; i++) {
            List<? extends Map<String, Object>> result = balanceMeasureSourcesMapper.getMeasureDataSourcesOneDay(i, date);
            for (Map<String, Object> mapData : result) {
                if (maps.containsKey(mapData.get(SOURCE_KEY))) {
                    maps.put((String) mapData.get(SOURCE_KEY),
                            (Long) mapData.get(COUNTS_KEY) + maps.get(mapData.get(SOURCE_KEY)));
                } else {
                    maps.put((String) mapData.get(SOURCE_KEY), Long.valueOf(mapData.get(COUNTS_KEY).toString()));
                }
            }
        }
        if (maps.containsKey(null)) {
            maps.put("null", maps.get(null));
            maps.remove(null);
        }
        CustomerContextHolder.clearDataSource();

        return maps;
    }
}

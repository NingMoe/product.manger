package com.phicomm.product.manger.module.terminal;

import com.mongodb.BasicDBObject;
import com.phicomm.product.manger.model.terminal.TerminalCommonEntity;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;

import java.text.ParseException;
import java.util.List;

/**
 * 设备终端
 *
 * @author wei.yang on 2017/12/28
 */
public interface MongoQueryFactory {

    /**
     * 获取设备终端详情
     *
     * @param keys 分组的key
     * @return 详情
     */
    GroupByResults<BasicDBObject> groupQuery(String... keys);

    /**
     * 历史同步
     *
     * @param key key
     * @return 数据
     */
    List<TerminalCommonEntity> historyKeyGroup(String key) throws ParseException;

    /**
     * 查询，不分组
     *
     * @param keys 查询的条件
     * @return 数据
     */
    GroupByResults<BasicDBObject> groupYesterdayData(String... keys);
}

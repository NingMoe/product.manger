package com.phicomm.product.manger.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.phicomm.product.manger.model.trace.UserActivityInputInfo;
import com.phicomm.product.manger.model.trace.UserActivityTrace;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Indexes.ascending;
import static com.mongodb.client.model.Sorts.orderBy;

/**
 * 用户活跃度处理逻辑
 *
 * @author qiang.ren
 * @date 2017/12/29
 */
@Service
public class UserActivityService {

    private MongoTemplate mongoTemplate;

    private static final Logger logger = Logger.getLogger(UserActivityService.class);
    private static final String USER_ACTIVITY_TRACE = "user_activity_trace";

    @Autowired
    public UserActivityService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        Assert.notNull(this.mongoTemplate);
    }

    /**
     * 统计24小时用户活跃度（PV）
     * @param userActivityInputInfo 用户活跃度传入信息
     * @return 24小时用户活跃度
     */
    public UserActivityTrace traceUserActivityPV(UserActivityInputInfo userActivityInputInfo){
        UserActivityTrace userActivityTrace = new UserActivityTrace();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(new DateTime().minusDays(1).toDate());
        List<List<Object>> list = Lists.newArrayList();
        if (null == userActivityInputInfo.getUserId() && null == userActivityInputInfo.getDate()){
            list.add(getPVData(today));
            list.add(getPVData(yesterday));
        }else if(null == userActivityInputInfo.getUserId() && null != userActivityInputInfo.getDate()){
            list.add(getPVData(today));
            list.add(getPVData(userActivityInputInfo.getDate()));
        }else if(null != userActivityInputInfo.getUserId() && null == userActivityInputInfo.getDate()){
            list.add(getUserPVData(today, userActivityInputInfo.getUserId()));
            list.add(getUserPVData(yesterday, userActivityInputInfo.getUserId()));
        }else{
            list.add(getUserPVData(today, userActivityInputInfo.getUserId()));
            list.add(getUserPVData(userActivityInputInfo.getDate(), userActivityInputInfo.getUserId()));
        }
        userActivityTrace.setData(list);
        return userActivityTrace;
    }

    /**
     * 统计24小时用户活跃度（UV）
     * @param userActivityInputInfo 用户活跃度传入信息
     * @return 24小时用户活跃度
     */
    public UserActivityTrace traceUserActivityUV(UserActivityInputInfo userActivityInputInfo){
        UserActivityTrace userActivityTrace = new UserActivityTrace();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(new DateTime().minusDays(1).toDate());
        List<List<Object>> list = Lists.newArrayList();
        if (null == userActivityInputInfo.getUserId() && null == userActivityInputInfo.getDate()){
            list.add(getPVData(today));
            list.add(getPVData(yesterday));
        }else if(null == userActivityInputInfo.getUserId() && null != userActivityInputInfo.getDate()){
            list.add(getPVData(today));
            list.add(getPVData(userActivityInputInfo.getDate()));
        }else if(null != userActivityInputInfo.getUserId() && null == userActivityInputInfo.getDate()){
            list.add(getUserPVData(today, userActivityInputInfo.getUserId()));
            list.add(getUserPVData(yesterday, userActivityInputInfo.getUserId()));
        }else{
            list.add(getUserPVData(today, userActivityInputInfo.getUserId()));
            list.add(getUserPVData(userActivityInputInfo.getDate(), userActivityInputInfo.getUserId()));
        }
        userActivityTrace.setData(list);
        return userActivityTrace;
    }

    /**
     * 通过day获取用户活跃度（PV）
     * @param day 日期 格式为"yyyy-MM-dd"
     * @return 该日期24小时的用户活跃度数据
     */
    private List<Object> getPVData(String day){
        List<Object> result = Lists.newArrayList();
        Map<Object, Object> map = Maps.newHashMap();
        for(int i =0;i<24;i++){
            map.put(i,0);
        }
        Block<Document> printBlock = document -> {
            Object key = document.get("_id");
            Object val = document.get("count");
            map.put(key,val);
        };
        MongoCollection<Document> collection = link(USER_ACTIVITY_TRACE);
        collection.aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.eq("date", day)),
                        Aggregates.group("$hour", Accumulators.sum("count", 1)),
                        Aggregates.sort(orderBy(ascending("_id")))
                )
        ).forEach(printBlock);
        for(int i =0;i<24;i++){
            result.add(map.get(i));
        }
        logger.info(day+"数据："+result);
        return result;
    }

    /**
     * 通过day和userId获取单个用户的活跃度（PV）
     * @param day 日期 格式为"yyyy-MM-dd"
     * @param userId 用户Id
     * @return 该日期,该用户24小时的用户活跃度数据
     */
    private List<Object> getUserPVData(String day, String userId){
        List<Object> result = Lists.newArrayList();
        Block<Document> printBlock = document -> result.add(document.get("count"));
        MongoCollection<Document> collection = link(USER_ACTIVITY_TRACE);
        collection.aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.and(Filters.eq("date", day), Filters.eq("userId", userId))),
                        Aggregates.group("$hour", Accumulators.sum("count", 1)),
                        Aggregates.sort(orderBy(ascending("_id")))
                )
        ).forEach(printBlock);
        logger.info(day+"数据："+result);
        return result;
    }

    /**
     * 通过day获取用户活跃度（UV）
     * @param day 日期 格式为"yyyy-MM-dd"
     * @return 该日期24小时的用户活跃度数据
     */
    private List<Object> getUVData(String day){
        List<Object> result = Lists.newArrayList();
        Block<Document> printBlock = document -> result.add(document.get("count"));
        MongoCollection<Document> collection = link(USER_ACTIVITY_TRACE);
        collection.aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.eq("date", day)),
                        Aggregates.group("$hour", Accumulators.sum("count", 1)),
                        Aggregates.sort(orderBy(ascending("_id")))
                )
        ).forEach(printBlock);
        logger.info(day+"数据："+result);
        return result;
    }

    private MongoCollection<Document> link(String traceStr){
        return mongoTemplate.getCollection(traceStr);
    }
}

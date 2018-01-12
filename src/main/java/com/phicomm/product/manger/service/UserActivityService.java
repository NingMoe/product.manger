package com.phicomm.product.manger.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.phicomm.product.manger.dao.UserActivityMapper;
import com.phicomm.product.manger.model.trace.UserActivityInfo;
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
import java.util.function.Consumer;

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

    private UserActivityMapper userActivityMapper;

    private static final Logger logger = Logger.getLogger(UserActivityService.class);
    private static final String USER_ACTIVITY_TRACE = "user_activity_trace";
    private static final String PV = "PV";
    private static final String UV = "UV";
    private static final int HOURS = 24;

    @Autowired
    public UserActivityService(MongoTemplate mongoTemplate,
                               UserActivityMapper userActivityMapper) {
        this.mongoTemplate = mongoTemplate;
        this.userActivityMapper = userActivityMapper;
        Assert.notNull(this.mongoTemplate);
        Assert.notNull(this.userActivityMapper);
    }

    /**
     * 统计24小时用户活跃度（PV）
     *
     * @param userActivityInputInfo 用户活跃度传入信息
     * @return 24小时用户活跃度
     */
    public UserActivityTrace traceUserActivityPV(UserActivityInputInfo userActivityInputInfo) {
        UserActivityTrace userActivityTrace = new UserActivityTrace();
        List<List<Object>> list = dealData(userActivityInputInfo, PV);
        userActivityTrace.setData(list);
        return userActivityTrace;
    }

    /**
     * 统计24小时用户活跃度（UV）
     *
     * @param userActivityInputInfo 用户活跃度传入信息
     * @return 24小时用户活跃度
     */
    public UserActivityTrace traceUserActivityUV(UserActivityInputInfo userActivityInputInfo) {
        UserActivityTrace userActivityTrace = new UserActivityTrace();
        List<List<Object>> list = dealData(userActivityInputInfo, UV);
        userActivityTrace.setData(list);
        return userActivityTrace;
    }

    /**
     * 具体逻辑处理
     *
     * @return 活跃度list
     */
    private List<List<Object>> dealData(UserActivityInputInfo userActivityInputInfo, String type) {
        List<List<Object>> list = Lists.newArrayList();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(new DateTime().minusDays(1).toDate());
        if (null == userActivityInputInfo.getUserId() && null == userActivityInputInfo.getDate()) {
            list.add(getList(userActivityMapper.getUserActivity(today, type)));
            if (1 != userActivityMapper.isExistUserActivity(yesterday, type)) {
                if (PV.equals(type)) {
                    list.add(getPVData(yesterday));
                    syncUserActivityInfo(yesterday, PV);
                } else {
                    list.add(getUVData(yesterday));
                    syncUserActivityInfo(yesterday, UV);
                }
            } else {
                list.add(getList(userActivityMapper.getUserActivity(yesterday, type)));
            }
        } else if (null == userActivityInputInfo.getUserId() && null != userActivityInputInfo.getDate()) {
            list.add(getList(userActivityMapper.getUserActivity(today, type)));
            if (1 != userActivityMapper.isExistUserActivity(userActivityInputInfo.getDate(), type)) {
                if (PV.equals(type)) {
                    list.add(getPVData(userActivityInputInfo.getDate()));
                } else {
                    list.add(getUVData(userActivityInputInfo.getDate()));
                }
            } else {
                list.add(getList(userActivityMapper.getUserActivity(userActivityInputInfo.getDate(), type)));
            }
        } else if (null != userActivityInputInfo.getUserId() && null == userActivityInputInfo.getDate()) {
            list.add(getUserPVData(today, userActivityInputInfo.getUserId()));
            list.add(getUserPVData(yesterday, userActivityInputInfo.getUserId()));
        } else {
            list.add(getUserPVData(today, userActivityInputInfo.getUserId()));
            list.add(getUserPVData(userActivityInputInfo.getDate(), userActivityInputInfo.getUserId()));
        }
        return list;
    }

    /**
     * 通过day获取用户活跃度（PV）
     *
     * @param day 日期 格式为"yyyy-MM-dd"
     * @return 该日期24小时的用户活跃度数据
     */
    private List<Object> getPVData(String day) {
        List<Object> result = Lists.newArrayList();
        Map<Object, Object> map = initMap();
        Block<Document> printBlock = document -> {
            Object key = document.get("_id");
            Object val = document.get("count");
            map.put(key, val);
        };
        MongoCollection<Document> collection = link(USER_ACTIVITY_TRACE);
        for (Document document : collection.aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.eq("date", day)),
                        Aggregates.group("$hour", Accumulators.sum("count", 1)),
                        Aggregates.sort(orderBy(ascending("_id")))
                )
        )) {
            printBlock.apply(document);
        }
        for (int i = 0; i < HOURS; i++) {
            result.add(map.get(i));
        }
        logger.info(day + "同步的PV数据：" + result);
        return result;
    }

    /**
     * 通过day和userId获取单个用户的活跃度（PV）
     *
     * @param day    日期 格式为"yyyy-MM-dd"
     * @param userId 用户Id
     * @return 该日期, 该用户24小时的用户活跃度数据
     */
    private List<Object> getUserPVData(String day, String userId) {
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
        logger.info(day + "数据：" + result);
        return result;
    }

    /**
     * 通过day获取用户活跃度（UV）
     *
     * @param day 日期 格式为"yyyy-MM-dd"
     * @return 该日期24小时的用户活跃度数据
     */
    private List<Object> getUVData(String day) {
        List<Object> result = Lists.newArrayList();
        Map<Object, Object> map = initMap();
        MongoCollection<Document> collection = link(USER_ACTIVITY_TRACE);
        Document match = new Document("date", day);
        Document group = new Document("_id", new Document("userId", "$userId")
                .append("hour", "$hour"));
        Document group1 = new Document("_id", new Document("hour", "$_id.hour"))
                .append("count", new Document("$sum", 1));
        AggregateIterable<Document> aggregate = collection
                .aggregate(Arrays.asList(new Document("$match", match), new Document("$group", group), new Document("$group", group1)));
        aggregate.forEach((Consumer<Document>) document -> {
            Object key = Integer.valueOf(JSON.parseObject(JSON.toJSONString(document.get("_id"))).getString("hour"));
            Object val = document.get("count").toString();
            map.put(key, val);
        });
        for (int i = 0; i < HOURS; i++) {
            result.add(map.get(i));
        }
        logger.info(day + "同步的UV数据：" + result);
        return result;
    }

    /**
     * 初始化map
     *
     * @return map
     */
    private Map<Object, Object> initMap() {
        Map<Object, Object> map = Maps.newHashMap();
        for (int i = 0; i < HOURS; i++) {
            map.put(i, 0);
        }
        return map;
    }

    /**
     * 同步日期为day的数据
     */
    public void syncUserActivityInfo(String day, String type) {
        List<Object> todayList;
        if (PV.equals(type)) {
            todayList = getPVData(day);
        } else {
            todayList = getUVData(day);
        }
        UserActivityInfo userActivityInfo = new UserActivityInfo(day, type, todayList.toString(), getTotalNum(todayList), new Date(), new Date());
        if (1 != userActivityMapper.isExistUserActivity(day, type)) {
            userActivityMapper.addUserActivity(userActivityInfo);
        } else {
            userActivityMapper.updateUserActivity(userActivityInfo);
        }

    }

    /**
     * 从UserActivityInfo中读取用户活跃度
     *
     * @return List<Object>
     */
    private List<Object> getList(UserActivityInfo userActivityInfo) {
        List<Object> list = Lists.newArrayList();
        String activityDate = userActivityInfo.getActivityDate();
        int length = activityDate.length();
        String[] activityDates = activityDate.substring(1, length - 1).split(",");
        for (String s : activityDates) {
            list.add(Integer.valueOf(s.trim()));
        }
        return list;
    }

    /**
     * 计算活跃度总数
     *
     * @param list List<Object>
     * @return 活跃度总数
     */
    private int getTotalNum(List<Object> list) {
        int result = 0;
        for (Object object : list) {
            result += Integer.parseInt(object.toString());
        }
        return result;
    }

    private MongoCollection<Document> link(String traceStr) {
        return mongoTemplate.getCollection(traceStr);
    }
}

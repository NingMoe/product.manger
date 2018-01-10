package com.phicomm.product.manger.service;

import com.google.common.collect.Lists;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.phicomm.product.manger.model.trace.UserActivityInputInfo;
import com.phicomm.product.manger.model.trace.UserActivityTrace;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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


    private static final Logger logger = Logger.getLogger(UserActivityService.class);
    private static final String USER_ACTIVITY_TRACE = "user_activity_trace";

    /**
     * 统计24小时用户活跃度（PV）
     * @param userActivityInputInfo 用户活跃度传入信息
     * @return 24小时用户活跃度
     */
    public UserActivityTrace traceUserActivityPV(UserActivityInputInfo userActivityInputInfo){
        UserActivityTrace userActivityTrace = new UserActivityTrace();
        Date today = new Date();
        Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<List<Object>> list = Lists.newArrayList();
        if (null == userActivityInputInfo.getUserId() && null == userActivityInputInfo.getDate()){
            list.add(getPVData(df.format(today)));
            list.add(getPVData(df.format(yesterday)));
        }else if(null == userActivityInputInfo.getUserId() && null != userActivityInputInfo.getDate()){
            list.add(getPVData(df.format(today)));
            list.add(getPVData(userActivityInputInfo.getDate()));
        }else if(null != userActivityInputInfo.getUserId() && null == userActivityInputInfo.getDate()){
            list.add(getUserPVData(df.format(today), userActivityInputInfo.getUserId()));
            list.add(getUserPVData(df.format(yesterday), userActivityInputInfo.getUserId()));
        }else{
            list.add(getUserPVData(df.format(today), userActivityInputInfo.getUserId()));
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
        Date today = new Date();
        Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<List<Object>> list = Lists.newArrayList();
        if (null == userActivityInputInfo.getUserId() && null == userActivityInputInfo.getDate()){
            list.add(getPVData(df.format(today)));
            list.add(getPVData(df.format(yesterday)));
        }else if(null == userActivityInputInfo.getUserId() && null != userActivityInputInfo.getDate()){
            list.add(getPVData(df.format(today)));
            list.add(getPVData(userActivityInputInfo.getDate()));
        }else if(null != userActivityInputInfo.getUserId() && null == userActivityInputInfo.getDate()){
            list.add(getUserPVData(df.format(today), userActivityInputInfo.getUserId()));
            list.add(getUserPVData(df.format(yesterday), userActivityInputInfo.getUserId()));
        }else{
            list.add(getUserPVData(df.format(today), userActivityInputInfo.getUserId()));
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
        Block<Document> printBlock = document -> result.add(document.get("count"));
        MongoCollection<Document> collection = link("trace",USER_ACTIVITY_TRACE);
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

    /**
     * 通过day和userId获取单个用户的活跃度（PV）
     * @param day 日期 格式为"yyyy-MM-dd"
     * @param userId 用户Id
     * @return 该日期,该用户24小时的用户活跃度数据
     */
    private List<Object> getUserPVData(String day, String userId){
        List<Object> result = Lists.newArrayList();
        Block<Document> printBlock = document -> result.add(document.get("count"));
        MongoCollection<Document> collection = link("trace",USER_ACTIVITY_TRACE);
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
        MongoCollection<Document> collection = link("trace",USER_ACTIVITY_TRACE);
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

    private MongoCollection<Document> link(String databaseName, String traceStr){
        ServerAddress serverAddress = new ServerAddress("172.16.99.220" , 4000);
        List<ServerAddress> addrs = new ArrayList<>();
        addrs.add(serverAddress);
        MongoCredential credential = MongoCredential.createScramSha1Credential("tracer", "trace", "123456".toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);
        MongoClient mongoClient = new MongoClient(addrs,credentials);
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        return database.getCollection(traceStr);
    }
}

package com.phicomm.product.manger.service;

import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import com.phicomm.product.manger.model.trace.UserActivityInputInfo;
import com.phicomm.product.manger.model.trace.UserActivityTrace;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    private static final int[] yesterday1 = new int[]{1295,429,196,162,270,1264,4280,5717,5192,4747,4877,5076,5294,4957,4582,4883,5422,6745,8710,9888,10460,10287,7172,3261};
    private static final int[] today1 = new int[]{1127,419,198,168,280,1121,4055,5571,4746,4702,4887,5231,5394,5169,4876,1320,0,0,0,0,0,0,0,0};

    @Autowired
    MongoTemplate mongoTemplate;

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
        List<int[]> list = Lists.newArrayList();
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
        if (null == userActivityInputInfo.getUserId() && null == userActivityInputInfo.getDate()){
            List<int[]> list = Lists.newArrayList();
            list.add(today1);
            list.add(yesterday1);
            userActivityTrace.setData(list);

        }else if(null == userActivityInputInfo.getUserId() && null != userActivityInputInfo.getDate()){

        }else if(null != userActivityInputInfo.getUserId() && null == userActivityInputInfo.getDate()){

        }else{

        }
        return userActivityTrace;
    }

    /**
     * 通过day获取用户活跃度（PV）
     * @param day 日期 格式为"yyyy-MM-dd"
     * @return 该日期24小时的用户活跃度数据
     */
    private int[] getPVData(String day){
        int[] result = new int[24];
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        MongoCollection<Document> collection = mongoTemplate.getCollection(USER_ACTIVITY_TRACE);
        long startTimeStamp = 0;
        long endTimeStamp;
        try {
            startTimeStamp = df.parse(day).getTime();
        } catch (ParseException e) {
            logger.info(e.getMessage(), e);
        }
        for(int i=0;i<24;i++){
            endTimeStamp = startTimeStamp+60*60*1000;
            Document match = new Document("$and", Arrays.asList(
                    new Document("timestamp", new Document("$gte", startTimeStamp)),
                    new Document("timestamp", new Document("$lt", endTimeStamp))
            ));
            int count = (int) collection.count(match);
            result[i] = count;
            startTimeStamp = startTimeStamp + 60*60*1000;
        }
        return result;
    }

    /**
     * 通过day获取用户活跃度（PV）
     * @param day 日期 格式为"yyyy-MM-dd"
     * @param userId 用户Id
     * @return 该日期,该用户24小时的用户活跃度数据
     */
    private int[] getUserPVData(String day, String userId){
        int[] result = new int[24];
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        MongoCollection<Document> collection = mongoTemplate.getCollection(USER_ACTIVITY_TRACE);
        long startTimeStamp = 0;
        long endTimeStamp;
        try {
            startTimeStamp = df.parse(day).getTime();
        } catch (ParseException e) {
            logger.info(e.getMessage(), e);
        }
        for(int i=0;i<24;i++){
            endTimeStamp = startTimeStamp+60*60*1000;
            Document match = new Document("$and", Arrays.asList(
                    new Document("timestamp", new Document("$gte", startTimeStamp)),
                    new Document("timestamp", new Document("$lt", endTimeStamp)),
                    new Document("userId", userId)
            ));
            int count = (int) collection.count(match);
            result[i] = count;
            startTimeStamp = startTimeStamp + 60*60*1000;
        }
        return result;
    }

    /**
     * 通过day获取用户活跃度（UV）
     * @param day 日期 格式为"yyyy-MM-dd"
     * @return 该日期24小时的用户活跃度数据
     */
    private int[] getUVData(String day){
        int[] result = new int[24];
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        MongoCollection<Document> collection = mongoTemplate.getCollection(USER_ACTIVITY_TRACE);
        long startTimeStamp = 0;
        long endTimeStamp;
        try {
            startTimeStamp = df.parse(day).getTime();
        } catch (ParseException e) {
            logger.info(e.getMessage(), e);
        }
        for(int i=0;i<24;i++){
            endTimeStamp = startTimeStamp+60*60*1000;
            Document match = new Document("$and", Arrays.asList(
                    new Document("timestamp", new Document("$gte", startTimeStamp)),
                    new Document("timestamp", new Document("$lt", endTimeStamp))
            ));
            int count = (int) collection.count(match);
            result[i] = count;
            startTimeStamp = startTimeStamp + 60*60*1000;
        }
        return result;
    }
}

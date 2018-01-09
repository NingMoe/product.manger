package com.phicomm.product.manger.wei.yang.mongo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.phicomm.product.manger.model.terminal.TerminalCommonEntity;
import com.phicomm.product.manger.utils.MongoDbUtil;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 测试mongoTemplate
 *
 * @author wei.yang on 2017/12/28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/spring-root.xml"})
public class TerminalTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @SuppressWarnings("all")
    public void test() {
        Criteria criteria = Criteria.where("createDate").lt("2017-12-29");
        GroupBy groupBy = GroupBy
                .key("equipmentTerminalInfo.systemInfo.platform", "equipmentTerminalInfo.appInfo.channel", "createDate")
                .initialDocument("{ count: 0 }")
                .reduceFunction("function(doc, prev) { prev.count += 1 }");
        GroupByResults<BasicDBObject> results = mongoTemplate
                .group(criteria, "equipment_terminal_detail_trace",
                        groupBy,
                        BasicDBObject.class);
        System.out.println(JSON.toJSONString(results));
        Iterator<BasicDBObject> iterator = results.iterator();
        while (iterator.hasNext()) {
            Map map = JSON.toJavaObject((JSON) JSON.toJSON(iterator.next()), Map.class);
            System.out.println(map);
        }
    }

    /**
     * 失败的尝试，group的一定要是路径
     */
    @Test
    public void aggregationTest() {
        AggregationResults<BasicDBObject> basicDBObjects = mongoTemplate
                .aggregate(Aggregation.newAggregation(
                        Aggregation.group("platform", "channel")
                                .count()
                                .as("count")
                ), "equipment_terminal_detail_trace", BasicDBObject.class);
        System.out.println(JSON.toJSONString(basicDBObjects));
    }

    /**
     * 尝试直接将timestamp在这个使用方法中转为固定格式时间失败
     */
    @Test
    @SuppressWarnings("all")
    public void projectTest() {
        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("equipmentTerminalInfo.systemInfo.platform").as("platform")
                .andExpression("equipmentTerminalInfo.appInfo.channel").as("$compareObject")
                .andExpression("createDate").as("createTime");
        GroupOperation groupOperation = Aggregation
                .group("platform", "$compareObject", "createTime")
                .count().as("count");
        AggregationResults<BasicDBObject> basicDBObjects = mongoTemplate
                .aggregate(TypedAggregation.newAggregation(
                        projectionOperation,
                        groupOperation
                ), "equipment_terminal_detail_trace", BasicDBObject.class);
        Iterator<BasicDBObject> iterator = basicDBObjects.iterator();
        while (iterator.hasNext()) {
            TerminalCommonEntity entity = JSON.toJavaObject((JSON) JSON.toJSON(iterator.next()), TerminalCommonEntity.class);
            System.out.println(JSONObject.toJSONString(entity));
        }
    }

    /**
     * 文档构建
     * db.equipment_terminal_detail_trace.aggregate(
     * {
     * $project : {
     * _id : "$equipmentTerminalInfo.userId",
     * date :  {$dateToString : {format:'%Y-%m-%d',date:{$add: [new Date(0), "$timestamp"]}}},
     * network:'$equipmentTerminalInfo.systemInfo.networkType',
     * platform:'$equipmentTerminalInfo.systemInfo.platform'
     * }
     * },
     * {
     * $group :{
     * _id:{userId:'$platform',network:'$network',date:'$date'},
     * count: {'$sum': 1}
     * }
     * }
     * );
     */
    @Test
    public void docTest() throws ParseException {
        MongoCollection<Document> collection = mongoTemplate.getCollection("equipment_terminal_detail_trace");
        Document time = MongoDbUtil.timeFormat("%Y-%m-%d", "timestamp");
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        Document project = new Document("createTime", time)
                .append("platform", "$equipmentTerminalInfo.systemInfo.platform")
                .append("compareObject", "$equipmentTerminalInfo.appInfo.channel");
        Document match = new Document("timestamp", new Document("$lt", new SimpleDateFormat("yyyy-MM-dd").parse(yesterday.toString()).getTime()));
        Document group = new Document("_id", new Document("platform", "$platform")
                .append("createTime", "$createTime")
                .append("compareObject", "$compareObject"))
                .append("count", new Document("$sum", 1));
        AggregateIterable<Document> result = collection
                .aggregate(Arrays.asList(new Document("$match", match), new Document("$project", project), new Document("$group", group)));
        result.forEach((Consumer<Document>) document -> {
            Map objectMap = JSON.toJavaObject(JSON.parseObject(document.toJson()), Map.class);
            TerminalCommonEntity entity = JSON.toJavaObject((JSON) objectMap.get("_id"), TerminalCommonEntity.class);
            entity.setCount((Integer) objectMap.get("count"));
            System.out.println(JSONObject.toJSONString(entity));
        });
    }
}

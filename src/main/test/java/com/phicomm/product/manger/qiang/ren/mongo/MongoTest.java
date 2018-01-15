package com.phicomm.product.manger.qiang.ren.mongo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mongodb.*;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.phicomm.product.manger.model.terminal.TerminalCommonEntity;
import com.phicomm.product.manger.utils.MongoDbUtil;
import org.bson.Document;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.mongodb.client.model.Indexes.ascending;
import static com.mongodb.client.model.Sorts.orderBy;

/**
 * mongodb test
 * Created by qiang.ren on 2018/1/5.
 */
@WebAppConfiguration
@ContextConfiguration({"/spring/spring-root.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    public MongoCollection<Document> linkTest(String traceStr){
        return mongoTemplate.getCollection(traceStr);
    }

    @Test
    public void insertTest(){
        MongoCollection<Document> collection = linkTest("user_activity_trace");

        Document doc = new Document("traceType", "user_activity_trace")
                .append("others", "ren")
                .append("timestamp", 99);
        collection.insertOne(doc);

        Block<Document> printBlock = document -> System.out.println(document.toJson());
        collection.find(Filters.and(Filters.gt("timestamp", 0), Filters.lte("timestamp", 100))).forEach(printBlock);
    }

    @Test
    public void findTest(){
        MongoCollection<Document> collection = linkTest("user_activity_trace");

        System.out.println("记录总数："+collection.count());

        System.out.println("77记录总数："+collection.count(Filters.eq("timestamp", 77)));

        Document myDoc = collection.find().first();
        System.out.println("第一个记录："+myDoc.toJson());

        //myDoc = collection.find(Filters.eq("others", "renqiang2")).first();
        //System.out.println("第一个符合条件的记录："+myDoc.toJson());

        Block<Document> printBlock = document -> System.out.println(document.toJson());
        //collection.find(Filters.gt("timestamp", 100)).forEach(printBlock);
        collection.find(Filters.and(Filters.gt("timestamp", 0), Filters.lte("timestamp", 100))).forEach(printBlock);
    }

    @Test
    public void updateTest(){
        MongoCollection<Document> collection = linkTest("user_activity_trace");

        collection.updateOne(Filters.eq("timestamp", 99), new Document("$set", new Document("timestamp", 77)));
        //collection.updateMany(Filters.eq("timestamp", 99), new Document("$set", new Document("timestamp", 77))).getModifiedCount();
        Block<Document> printBlock = document -> System.out.println(document.toJson());
        collection.find(Filters.and(Filters.gt("timestamp", 0), Filters.lte("timestamp", 100))).forEach(printBlock);
    }

    @Test
    public void deleteTest(){
        MongoCollection<Document> collection = linkTest("user_activity_trace");
        //collection.deleteOne(Filters.gt("timestamp", 77));
        collection.deleteMany(Filters.eq("timestamp", 77)).getDeletedCount();
        Block<Document> printBlock = document -> System.out.println(document.toJson());
        collection.find(Filters.and(Filters.gt("timestamp", 0), Filters.lte("timestamp", 100))).forEach(printBlock);
    }

    @Test
    public void aggregateTest(){
        List<Object> result = Lists.newArrayList();
        Block<Document> printBlock = document -> result.add(document.get("count"));
        MongoCollection<Document> collection = linkTest("user_activity_trace");
        collection.aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.eq("date", "2018-01-10")),
                        Aggregates.group("$hour,$userId"),
                        Aggregates.group("$_id.hour", Accumulators.sum("count", 1))
                        //[1, 3, 2, 2, 1, 1, 1]
                )
        ).forEach(printBlock);
        System.out.println("2018-01-10"+"数据："+result);
    }

    @Test
    public void docTest() throws ParseException {
        MongoCollection<Document> collection = mongoTemplate.getCollection("user_activity_trace");
        Document match = new Document("date", "2018-01-10");
        Document group = new Document("_id", new Document("userId", "$userId")
                .append("hour", "$hour"));
        Document group1 = new Document("_id", new Document("hour", "$_id.hour"))
                .append("count", new Document("$sum",1));
        AggregateIterable<Document> result = collection
                .aggregate(Arrays.asList(new Document("$match", match), new Document("$group", group),new Document("$group",group1)));
        result.forEach((Consumer<Document>) document -> {
            System.out.printf(document.toJson());
            System.out.printf(JSON.parseObject(JSON.toJSONString(document.get("_id"))).getString("hour"));
            System.out.printf(document.get("count").toString());
        });
    }

    /*@Test
    public void aggregateTest2(){
        Block<Document> printBlock = document -> System.out.println(document.toJson());
        MongoCollection<Document> collection = linkTest("trace","user_activity_trace");
        collection.aggregate(
                Arrays.asList(
                        Aggregates.project(
                                Projections.fields(
                                        Projections.excludeId(),
                                        Projections.include("others"),
                                        Projections.computed(
                                                "xin",
                                                new Document("$arrayElemAt", Arrays.asList("$arrayfiled", 0))
                                        )
                                )
                        )
                )
        ).forEach(printBlock);
    }*/

    @Test
    public void test(){
        System.out.println(LocalDate.now().toString("yyyy-MM-dd"));
        System.out.println(new SimpleDateFormat("HH").format(new Date()));
    }
}

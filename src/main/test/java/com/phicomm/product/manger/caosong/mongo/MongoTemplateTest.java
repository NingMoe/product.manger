package com.phicomm.product.manger.caosong.mongo;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.phicomm.product.manger.utils.MongoDbUtil;
import org.bson.BsonDateTime;
import org.bson.Document;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import scala.io.BytePickle;

import javax.print.Doc;
import java.util.*;

import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

/**
 * Created by song02.cao on 2017/12/28.
 */
@WebAppConfiguration
@ContextConfiguration({"/spring/spring-root.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoTemplateTest {

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 获取collection
     */
    @Test
    public void getCollecionTest() {
        //获取collection
        MongoCollection<Document> collection = mongoTemplate.getCollection("test");
    }

    /**
     * 删除collection
     */
    @Test
    public void dropCollectionTest() {
        mongoTemplate.dropCollection("test");
    }

    /**
     * 插入一个document
     */
    @Test
    public void insertDocumentTest() {
        //方法1 ：利用spring-mongodb的test
        JSONObject object = new JSONObject();
        object.put("key", "value");
        mongoTemplate.insert(object, "test");

        //方法2：
        Document document = new Document();
        document.put("key", "value2");
        mongoTemplate.getCollection("test").insertOne(document);

        //方法3： 注意该方法必须要spring 5.0.0以上，否则出错
//        Person caosong = new Person();
//        caosong.setName("caosong");
//        caosong.setAge(10);
//        mongoTemplate.insert(caosong, "people");

    }

    @Test
    public void deleteDocumentTest() {
    }

    @Test
    public void groupByTest() {
        //注：因为用到pojo 转成document，所以需要spring 5.0.0
//       GroupByResults<PersonCount> personCounts = mongoTemplate.group("people",
//                GroupBy.key("name","age").
//                        initialDocument("{count: 0}").
//                        reduceFunction("function(doc, prev){prev.count +=1 }"),PersonCount.class);
//        Iterator<PersonCount> results = personCounts.iterator();
//        while (results.hasNext()) {
//            System.out.println(results.next());
//        }
    }

    @Test
    public void groupByTest2() {
        //需要spring 5.0.0
//        Aggregation agg = newAggregation(
//                group("name").count().as("count")
//        );
//
//        AggregationResults<PersonCount>  results = mongoTemplate.aggregate(agg, "people", PersonCount.class);
//        Iterator<PersonCount> iterator = results.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
    }

    @Test
    public void test() {
        Block<Document> block = new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document.toJson());
            }
        };
        MongoCollection<Document> collection = mongoTemplate.getCollection("essay_statistic_trace");
        //group操作
        //match操作
        //addFields操作
        //project操作
        LocalDate today = new LocalDate();
        long todayTimestamp = today.toDate().getTime();
        LocalDate beforToday14 = today.minusDays(14);
        long beforToday14Timestamp = beforToday14.toDate().getTime();
        collection.aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.and(gte("timestamp", beforToday14Timestamp),
                                lt("timestamp", today))),
                        Aggregates.project(fields(include("timestamp", "shareType"))),
//                        Aggregates.addFields(new Field("date", new Date("$timestamp"))),
                        Aggregates.group("$shareType", Accumulators.sum("count", 1)),
                        Aggregates.addFields(new Field("shareType", "$_id")),
                        Aggregates.project(fields(excludeId()))
                )
        ).forEach(block);
    }

    @Test
    public void timeTest() {
        LocalDate localDate = new LocalDate();
        LocalDate localDate1 = localDate.minusDays(14);
        Date date = localDate.toDate();
        Date date1 = localDate1.toDate();
        System.out.println(date1);
//        System.out.println(localDate.toString());
//        Date date = new Date();
//        System.out.println(date);
//        System.out.println(date.getTime());
    }

    @Test
    public void formatDateTest() {
        Block<Document> block = new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        };

        LocalDate today = new LocalDate();
        long todayLong = today.toDate().getTime();
        long beforeToday14 = today.minusDays(14).toDate().getTime();

        MongoCollection<Document> collection = mongoTemplate.getCollection("essay_statistic_trace");

        Document match = new Document("timestamp", new Document("$gte", beforeToday14).
                append("timestamp", new Document("$lt", today)));

        Document project = new Document("shareType", 1).
                append("dateTime", new Document("$dateToString",
                        new Document("format", "%Y-%m-%d").
                                append("date", new Date("timestamp"))));

//        List list = Arrays.asList(new Document("$match", match), new Document("$project",project));
        List list = Arrays.asList(new Document("$match", match));
        collection.aggregate(list).forEach(block);
    }

    /**
     * 在聚合操作过程中，进行时间转换验证
     */
    @Test
    public void mongoTimeTest() {
        Block<Document> block = new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        };
        long time = new Date().getTime();
        System.out.println("caosong " + time);
        MongoCollection<Document> collection = mongoTemplate.getCollection("test");
        //new Date() 验证
        Document project = new Document("time", new Date(time));
        collection.aggregate(Arrays.asList(new Document("$project", project))).forEach(block);

        //$dateToString验证
        //将时间格式转换成如1988-09-12格式
        Document stringDate = new Document("$dateToString",
                new Document("format", "%Y-%m-%d").append("date", new Date()));
        Document project2 = new Document("time", stringDate);
        collection.aggregate(Arrays.asList(new Document("$project", project2))).forEach(block);
    }

    //    {$project: {time: {$dateToString: {format:"%Y-%m-%d", date: {$add:[new Date(0), 1514257127081]}}}}}

    /**
     * 聚合操作，时间转换工具验证
     */
    @Test
    public void timeStampTest() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test2");
//        Document timeDate = new Document("$add",Arrays.asList(new Date(0),"$timestamp"));
//        Document timeFormat = new Document("$dateToString", new Document("format", "%Y-%m-%d").append("date", timeDate));
//        Document project = new Document("key", timeFormat);
//        Document project = timeFormat("key", "%Y-%m-%d %H:%M:%S", "timestamp");
        Document time = MongoDbUtil.timeFormat("%Y-%m-%d", "timestamp");
        Document project = new Document("key", time);
        collection.aggregate(Arrays.asList(new Document("$project", project))).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        });
    }


    /**
     * 关于group的学习验证
     * {
     * $group: {
     * _id: <group的field>,
     * newField: <计算操作>
     * }
     * }
     */
    @Test
    public void groupTest() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test2");
        Document group = new Document("_id", new Document("name", "$name").append("timestamp", "$timestamp")).
                append("count", new Document("$sum", 1));
        Document project = new Document("name", "$_id.name").
                append("timestamp", "$_id.timestamp").
                append("count", 1);
        collection.aggregate(Arrays.asList(new Document("$group", group),
                new Document("$project", project))).
                forEach(new Block<Document>() {
                    @Override
                    public void apply(Document document) {
                        System.out.println(document);
                    }
                });
    }

    /**
     * 关于match的操作测试
     */
    @Test
    public void matchTest() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test2");
        LocalDate today = new LocalDate();
        LocalDate beforToday = today.minusDays(14);
        System.out.println(today.toDate().getTime());
        System.out.println(beforToday.toDate().getTime());

        //match中 and操作
        Document match = new Document("$and", Arrays.asList(
                new Document("timestamp", new Document("$gt", beforToday.toDate().getTime())),
                new Document("timestamp", new Document("$lt", today.toDate().getTime()))
        ));
        collection.aggregate(Arrays.asList(new Document("$match", match))).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        });

        //match中 or操作
        Document match2 = new Document("$or", Arrays.asList(
                new Document("timestamp", new Document("$gt", beforToday.toDate().getTime())),
                new Document("timestamp", new Document("$lt", today.toDate().getTime()))
        ));
        collection.aggregate(Arrays.asList(new Document("$match", match2))).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        });
    }

}


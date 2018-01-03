package com.phicomm.product.manger.caosong.mongo;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

/**
 * 聚合操作pipeline test
 */
@WebAppConfiguration
@ContextConfiguration({"/spring/spring-root.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AggregationPipelineTest {

    @Autowired
    MongoTemplate mongoTemplate;

/*
    mongodb的pipe line聚合操作需要理解两个概念：
    1. pipe line stage 操作函数，诸如：
            $limit 同sql中的limit
            $project 同sql中的select
            $group 同sql中的group by
            $match 同sql中的 where
            $sort 同sql中的sort
            $lookup 同sql中的join
         还有其他操作等等ref:https://docs.mongodb.com/manual/reference/operator/aggregation-pipeline/
    2. 每个stage中使用到的操作函数，诸如：//注意这些函数只能在聚合操作过程中使用
            $abs 求绝对值
            $avg 求平均值
            $cmp 比较大小
          等等。ref:https://docs.mongodb.com/manual/reference/operator/aggregation/
*/

    /**
     * project的测试
     */
    @Test
    public void projectTest() {

        MongoCollection<Document> collection = mongoTemplate.getCollection("test3");
/*
        1.同sql中的select，该例中的作用是，选择表中字段为item的列，并添加新的列newId,并赋值“2”
        2. db.test3.aggregate([{"$project":{item:1,newId:"2"}]}
        3. ref: https://docs.mongodb.com/manual/reference/operator/aggregation/project/#pipe._S_project
*/
        Document project = new Document("item", 1).append("newId", "2");
        collection.aggregate(Arrays.asList(
                new Document("$project", project)
        )).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        });
    }

    /**
     * limit test
     */
    @Test
    public void limitTest() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test3");
/*
        1. 同sql中的limit
        2. db.text3.aggregate([{"$limit": 5}])
        3. ref:https://docs.mongodb.com/manual/reference/operator/aggregation/limit/
*/
        collection.aggregate(Arrays.asList(
                new Document("$limit", 5)
        )).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        });
    }

    /**
     * match test
     */
    @Test
    public void matchTest() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test3");
/*
        1.同sql中的where
        2.db.test3.aggregate([{"$match":{item:"pager"}}])
        3.ref:https://docs.mongodb.com/manual/reference/operator/aggregation/match/
*/
        Document match = new Document("item", "pager");
        collection.aggregate(Arrays.asList(
                new Document("$match", match)
        )).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        });
    }

    /**
     * todo 待完善
     */
    @Test
    public void groupTest() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test4");
        DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();
        LocalDateTime time1 = LocalDateTime.parse("2014-03-01T08:00:00.000Z", dateTimeFormatter);
        LocalDateTime time2 = LocalDateTime.parse("2014-03-01T09:00:00.000Z", dateTimeFormatter);
        LocalDateTime time3 = LocalDateTime.parse("2014-03-15T09:00:00.000Z", dateTimeFormatter);
        LocalDateTime time4 = LocalDateTime.parse("2014-04-04T11:21:39.736Z", dateTimeFormatter);
        LocalDateTime time5 = LocalDateTime.parse("2014-04-04T11:21:39.736Z", dateTimeFormatter);
//        collection.insertMany(Arrays.asList(
//                new Document("item","abc").append("price",10).append("quantity",2).append("date",ISODate("2014-03-01T08:00:00Z")),
//                new Document("item","jkl").append("price",20).append("quantity",1).append("date",ISODate("2014-03-01T09:00:00Z")),
//                new Document("item","xyz").append("price",5).append("quantity",10).append("date",ISODate("2014-03-15T09:00:00Z")),
//                new Document("item","xyz").append("price",5).append("quantity",20).append("date",ISODate("2014-04-04T11:21:39.736Z")),
//                new Document("item","abc").append("price",10).append("quantity",10).append("date",ISODate("2014-04-04T21:23:13.736Z"))
//        ));

    }

}

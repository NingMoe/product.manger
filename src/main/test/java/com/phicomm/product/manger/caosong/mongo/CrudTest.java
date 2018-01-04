package com.phicomm.product.manger.caosong.mongo;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mongodb CRUD test
 *
 * @author song02.cao
 */
@WebAppConfiguration
@ContextConfiguration({"/spring/spring-root.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CrudTest {

    @Autowired
    MongoTemplate mongoTemplate;


    /**
     * 插入操作
     */
    @Test
    public void insertTest() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test");

        //insert one
        Document one = new Document("key", "test");
        collection.insertOne(one);

        //insert many
        List<Document> list = new ArrayList<>();
        Document two = new Document("key", "2");
        Document three = new Document("key", "3");
        Document four = new Document("key", "4");
        list.add(two);
        list.add(three);
        list.add(four);
        collection.insertMany(list);
    }

    /**
     * 查找相关指令
     */
    @Test
    public void queryTest() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test3");
/*        db.inventory.insertMany([
                { item: "journal", qty: 25, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
                { item: "notebook", qty: 50, size: { h: 8.5, w: 11, uom: "in" }, status: "A" },
                { item: "paper", qty: 100, size: { h: 8.5, w: 11, uom: "in" }, status: "D" },
                { item: "planner", qty: 75, size: { h: 22.85, w: 30, uom: "cm" }, status: "D" },
                { item: "postcard", qty: 45, size: { h: 10, w: 15.25, uom: "cm" }, status: "A" }
        ]);
*/
        collection.insertMany(Arrays.asList(
                new Document("item", "journal").append("qty", 25).append("size", new Document("h", 14).append("w", 12).append("uom", "cm")).append("status", "A"),
                new Document("item", "notebook").append("qty", 50).append("size", new Document("h", 8.5).append("w", 11).append("uom", "in")).append("status", "A"),
                new Document("item", "pager").append("qty", 100).append("size", new Document("h", 8.5).append("w", 11).append("uom", "in")).append("status", "D"),
                new Document("item", "planner").append("qty", 75).append("size", new Document("h", 22.85).append("w", 30).append("uom", "cm")).append("status", "D"),
                new Document("item", "postcard").append("qty", 45).append("size", new Document("h", 10).append("w", 15.25).append("uom", "cm")).append("status", "A")
        ));

//        db.test3.find( {} )
//        查找所有 类似于sql中的 select * from tablename
        collection.find().forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        });

//        相等条件的查找
//        db.test3.find({status:"A"});
        Document equal = new Document("status", "A");
        collection.find(equal).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println("status = A");
                System.out.println(document);
            }
        });

//        AND 条件查找
//        db.test3.find({"$and":[{status:"A"},{qty:50}]})
        Document cond1 = new Document("status", "A");
        Document cond2 = new Document("qty", 50);
        Document andCond = new Document("$and", Arrays.asList(cond1, cond2));
        System.out.println("======================================");
        collection.find(andCond).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        });

//        or 条件
//        db.test3.find({"$or":[{status:"A"},{qty:50}]})
        Document orCond = new Document("$or", Arrays.asList(cond1, cond2));
        System.out.println("=====================");
        collection.find(orCond).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        });

//        比较操作运算符 $eq $gt $gte $in $lt $lte $ne  $nin
//        db.test3.find({qty:{"$gt":50}})
        Document gtCond = new Document("$gt", 50);
        Document cond = new Document("qty", gtCond);
        System.out.println("============================");
        collection.find(cond).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        });
    }

    /**
     * 更新操作test
     */
    @Test
    public void updateTest() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test3");

        //updateOne 仅对符合条件的第一个document进行更新
/*        db.test3.updateOne(
                {item, "pager"},
                {
                    "$set": {"size.uom", "cm"},
                    "$currentDate":{lastModified:true}
                }
        );
*/
        Document filter = new Document("item", "pager");
        Document set = new Document("size.uom", "cm");
        collection.updateOne(filter, new Document("$set", set).
                append("$currentDate", new Document("lastModified", true)));

        //updateMany 对多个进行更新
/*        db.test3..updateOne(
                {   qty:{$gt:50}},
                {   $set: {"size.w":30},
                    $currentDate:{lastModified:true}
                }
        )
*/
        Document filter2 = new Document("qty", new Document("$gt", 50));
        Document set2 = new Document("size.w", 30);
        collection.updateMany(filter2, new Document("$set", set2).
                append("$currentDate", new Document("lastModified", true)));

        //replaceOne 替换第一个
/*        db.test3.replaceOne(
                {item:"pager"},
                {
                    item:"pager",
                    instock:[{warehouse:"A",qty:60},
                             {warehouse:"B",qty:40}]
                }
        );*/
        Document filter3 = new Document("item", "pager");
        Document place = new Document("item", "pager").append("instock",
                Arrays.asList(
                        new Document("warehouse", "A").append("qty", 60),
                        new Document("warehouse", "B").append("qty", 40)
                ));
        collection.replaceOne(filter3, place);

        //upsert 有则更新，无则插入 todo later
    }

    /**
     * 删除操作
     *
     * @todo 当前项目中暂未用到，后续再补充
     */
    @Test
    public void delteTest() {

    }

}

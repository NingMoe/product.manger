package com.phicomm.product.manger.caosong.mongo;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by song02.cao on 2017/12/29.
 */
@WebAppConfiguration
@ContextConfiguration({"/spring/spring-root.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TimeTest {

    @Autowired
    MongoTemplate mongoTemplate;
    @Test
    public void test() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test2");
        long newTimeField = 1513267200000L;
        LocalDate localDate = new LocalDate(newTimeField);
        System.out.println(localDate);
        String format ="%Y-%m-%d";
        Document timeDate = new Document("$add", Arrays.asList(new Date(0), newTimeField, 8 * 60 * 60 * 1000));
//        Document timeFormat = new Document("$dateToString", new Document("format", format).append("date", timeDate).append("timezone","+08"));
        Document timeFormat = new Document("$dateToString", new Document("format", format).append("date", timeDate));
        Document project = new Document("date", timeFormat);
        collection.aggregate(Arrays.asList(new Document("$project", project))).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                System.out.println(document);
            }
        });

    }
}

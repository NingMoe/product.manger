package com.phicomm.product.manger.caosong.mongo;

import com.mongodb.client.MongoCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by song02.cao on 2017/12/28.
 */
@WebAppConfiguration
@ContextConfiguration({"/spring/spring-root.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoTemplateTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void test() {
        long count = mongoTemplate.getCollection("test").count();
        long count2 = mongoTemplate.getCollection("user_activity_trace").count();
        System.out.println(count);
        System.out.println(count2);
    }
}

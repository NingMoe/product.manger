package com.phicomm.product.manger.wei.yang.mongo;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author wei.yang on 2017/12/28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/spring-root.xml"})
public class TerminalTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void test() {
        GroupBy groupBy = GroupBy
                .key("equipmentTerminalInfo.systemInfo.platform", "equipmentTerminalInfo.appInfo.channel")
                .initialDocument("{ count: 0 }")
                .reduceFunction("function(doc, prev) { prev.count += 1 }");
        GroupByResults<BasicDBObject> results = mongoTemplate
                .group("equipment_terminal_detail_trace",
                        groupBy,
                        BasicDBObject.class);
        System.out.println(JSON.toJSONString(results));
        AggregationResults<BasicDBObject> basicDBObjects = mongoTemplate
                .aggregate(Aggregation.newAggregation(
                        Aggregation.group("platform", "channel")
                                .count()
                                .as("count")
                ), "equipment_terminal_detail_trace", BasicDBObject.class);
        System.out.println(JSON.toJSONString(basicDBObjects));
    }
}

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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Iterator;
import java.util.Map;

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
        Criteria criteria = Criteria.where("createDate").lt("2017-12-29");
        GroupBy groupBy = GroupBy
                .key("equipmentTerminalInfo.systemInfo.platform", "equipmentTerminalInfo.appInfo.channel","createDate")
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
        AggregationResults<BasicDBObject> basicDBObjects = mongoTemplate
                .aggregate(Aggregation.newAggregation(
                        Aggregation.group("platform", "channel")
                                .count()
                                .as("count")
                ), "equipment_terminal_detail_trace", BasicDBObject.class);
        System.out.println(JSON.toJSONString(basicDBObjects));
    }
}

package com.phicomm.product.manger.dao;

import com.alibaba.fastjson.JSON;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.phicomm.product.manger.model.statistic.ShareStatisticModel;
import com.phicomm.product.manger.utils.MongoDbUtil;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 读取mongodb中分享相关数据
 *
 * @author song02.cao
 */

@Repository
public class ShareStatisticMapper {

    private MongoTemplate mongoTemplate;

    @Autowired
    public ShareStatisticMapper(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        Assert.notNull(this.mongoTemplate, "mongoTemplate is null");
    }

    /**
     * 从表中获取一段时间内，所有的分享类型每一天的分享总数
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 返回的所有的分享类型以及每天的分享总数
     */
    public List<ShareStatisticModel> getShareStatistic(long beginDate, long endDate) {
        List<ShareStatisticModel> list = new ArrayList<>();
        MongoCollection<Document> collection = mongoTemplate.getCollection("share_statistic_trace");

        Document match = new Document("$and", Arrays.asList(
                new Document("timestamp", new Document("$gte", beginDate)),
                new Document("timestamp", new Document("$lt", endDate))
        ));

        Document date = MongoDbUtil.timeFormat("%Y-%m-%d", "timestamp");
        Document project = new Document("shareType", 1).append("date", date);

        Document group = new Document("_id", new Document("shareType", "$shareType").append("shareDate", "$date")).
                append("shareCount", new Document("$sum", 1));

        Document project2 = new Document("shareType", "$_id.shareType").
                append("shareDate", "$_id.shareDate").
                append("shareCount", 1);

        collection.aggregate(Arrays.asList(
                new Document("$match", match),
                new Document("$project", project),
                new Document("$group", group),
                new Document("$project", project2)
        )).forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                list.add(JSON.toJavaObject(JSON.parseObject(document.toJson()), ShareStatisticModel.class));
            }
        });
        return list;
    }
}

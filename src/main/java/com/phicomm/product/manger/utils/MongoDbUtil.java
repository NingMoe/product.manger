package com.phicomm.product.manger.utils;

import org.bson.Document;

import java.util.Arrays;
import java.util.Date;

/**
 * mongodb工具类
 *
 * @author song02.cao
 */
public class MongoDbUtil {

    /**
     * 将表中long型的timestamp转换成诸如"2017-12-30 23:25:22"的格式，其中格式可以由format参数配置
     * 注： 用于在聚合操作过程中
     *
     * @param format    转换的格式,诸如"%Y-%m-%d %H:%M:%S"
     * @param timeField 表中要转换的timestamp的名称
     * @return 转换成功后的时间document
     */
    public static Document timeFormat(String format, String timeField) {
        String newTimeField = "$" + timeField;
        Document timeDate = new Document("$add", Arrays.asList(new Date(0), newTimeField, 8 * 60 * 60 * 1000));
        return new Document("$dateToString", new Document("format", format).append("date", timeDate));
    }
}

package com.phicomm.product.manger.utils;

import org.joda.time.LocalDateTime;

import java.util.Date;
import java.util.UUID;

/**
 * velocity使用的模板
 *
 * @author yufei.liu on 2017/6/3.
 */
public class VelocityUtil {

    /**
     * 获得一个简化日期字符串
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String toSimpleDate(Date date) {
        return new LocalDateTime(date).toString("MM. yyyy");
    }

    /**
     * 获取uuid
     *
     * @return uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}

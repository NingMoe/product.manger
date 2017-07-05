package com.phicomm.product.manger.yufei.liu;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * 测试日期
 * Created by yufei.liu on 2017/5/31.
 */
public class DateTest {

    @Test
    public void test1() {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDateTime dateTime = LocalDateTime.parse("1990-04-15", format);
        String dateStr = dateTime.toString("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateStr);
    }

    @Test
    public void test2() {
        LocalDateTime dateTime = LocalDateTime.parse("1990-04-15");
        String dateStr = dateTime.toString("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateStr);
    }

    @Test
    public void test3() {
        LocalDateTime dateTime = LocalDateTime.now();
        String dateStr = dateTime.toString("yyyy-MM-dd 00:00:00");
        System.out.println(dateStr);
    }

}

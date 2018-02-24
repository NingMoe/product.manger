package com.phicomm.product.manger.wei.yang.date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author wei.yang on 2017/12/29
 */
public class DateTest {

    @Test
    public void test() {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(1);
        System.out.println(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        LocalDate time = LocalDate.parse("2014-12-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(time);
    }

    @Test
    public void test1(){
        Date date=new Date(1515369600000L);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }
}

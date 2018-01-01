package com.phicomm.product.manger.wei.yang.date;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wei.yang on 2017/12/29
 */
public class DateTest {

    @Test
    public void test(){
        LocalDateTime dateTime=LocalDateTime.now().minusDays(1);
        System.out.println(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        LocalDate time = LocalDate.parse("2014-12-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(time);
    }
}

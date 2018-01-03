package com.phicomm.product.manger.caosong.others;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;


/**
 * joda time 验证
 *
 * @author song02.cao
 */

public class JodaTimeTest {

    /**
     * LocalDate.parse验证
     */
    @Test
    public void test() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2017-01-22", dateTimeFormatter);
        System.out.println(date.toString());
    }

    /**
     * 天数间隔Period验证
     */
    @Test
    public void test2() {
        LocalDate today = new LocalDate();
        LocalDate before = today.minusDays(14);
        Period period = new Period(before, today, PeriodType.days());
        int days = period.getDays();
        System.out.println(days);
    }
}

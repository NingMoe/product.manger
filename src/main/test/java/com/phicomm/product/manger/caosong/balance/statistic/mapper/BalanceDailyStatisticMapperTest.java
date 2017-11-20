package com.phicomm.product.manger.caosong.balance.statistic.mapper;

import com.phicomm.product.manger.dao.BalanceDailyStatisticMapper;
import com.phicomm.product.manger.model.statistic.BalanceAsHourModel;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by song02.cao on 2017/11/16.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/spring-root.xml"})
public class BalanceDailyStatisticMapperTest {

    @Autowired
    BalanceDailyStatisticMapper balanceDailyStatisticMapper;
    @Test
    public void testTimeRange() throws ParseException {
        CustomerContextHolder.selectTestDataSource();
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime="2017-01-01 00:00:00";//注：改正后这里前后也加了空格
        String endTime="2018-01-01 00:00:00";//注：改正后这里前后也加了空格
        Date startDate = format.parse(startTime);
        Date endDate = format.parse(endTime);
        System.out.print("Format To times:"+startDate.getTime());
        List<BalanceAsHourModel> balanceAsHourModelList = new ArrayList<>();
        balanceAsHourModelList.addAll(balanceDailyStatisticMapper.getBalanceStatisticHourUnitTimeRange(startDate,endDate, 1));
        System.out.println("caosong +++++++++++++++++++===========size is = " + balanceAsHourModelList.size());
    }
    @Test
    public void testOneDay() throws ParseException {
        CustomerContextHolder.selectTestDataSource();
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime="2017-08-29 00:00:00";//注：改正后这里前后也加了空格
        Date date = format.parse(dateTime);
        List<BalanceAsHourModel> balanceAsHourModelList = new ArrayList<>();
        balanceAsHourModelList.addAll(balanceDailyStatisticMapper.getBalanceStatisticHourUnitOneDay(date,1));
        System.out.println("caosong +++++++++++++++++++===========size is = " + balanceAsHourModelList.size());
    }
    @Test
    public void testAllDay() throws ParseException {
        CustomerContextHolder.selectTestDataSource();
        List<BalanceAsHourModel> balanceAsHourModelList = new ArrayList<>();
        balanceAsHourModelList.addAll(balanceDailyStatisticMapper.getBalanceStatisticHourUnitAll(10));
        System.out.println("caosong +++++++++++++++++++===========size is = " + balanceAsHourModelList.size());
    }
    @Test
    public void isertCountHour() {
        CustomerContextHolder.selectTestDataSource();
        balanceDailyStatisticMapper.setBalanceStatisticHourUnit(3,"2017-10-10 10");
    }
    @Test
    public void getHourSumAllDay() {
        CustomerContextHolder.selectTestDataSource();
        for (int i=1; i < 50; i++) {
            balanceDailyStatisticMapper.getBalanceStatistic24HourDisplay(i);
        }
    }
    @Test
    public void get24dispalyAllDay() {
        CustomerContextHolder.selectTestDataSource();
        List<BalanceAsHourModel> listAll = new ArrayList<>();
        for (int i =1 ;i <= 49; i++) {
            List<BalanceAsHourModel> list = new ArrayList<>();
            list = balanceDailyStatisticMapper.getBalanceStatistic24HourDisplay(i);
            for ( int j=0; j< listAll.size(); j++) {
                System.out.println(listAll.get(i).toString());
            }
        }
    }
    @Test
    public void getBalance24HourcCountTest() {
        CustomerContextHolder.selectTestDataSource();
        List<BalanceAsHourModel> list = new ArrayList<>();
        list = balanceDailyStatisticMapper.getBalance24HourcCount();
        System.out.println(list.toString());
    }
}

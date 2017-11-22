package com.phicomm.product.manger.caosong.balance.statistic.mapper;

import com.phicomm.product.manger.dao.BalanceDailyStatisticMapper;
import com.phicomm.product.manger.model.statistic.BalanceAsHourModel;
import com.phicomm.product.manger.model.statistic.BalanceElectrodeModel;
import com.phicomm.product.manger.model.statistic.BalanceMaxMinDatesModel;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void isertCountHour() {
        CustomerContextHolder.selectTestDataSource();
        balanceDailyStatisticMapper.setBalance24HourCount(3,"2017-10-10 10");
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
        CustomerContextHolder.selectLocalDataSource();
        List<BalanceAsHourModel> list = new ArrayList<>();
        list = balanceDailyStatisticMapper.getBalance24HourCount();
        System.out.println(list.toString());
    }


    @Test
    public void setBalanceElectrodeStatisticTest() throws ParseException {
        CustomerContextHolder.selectTestDataSource();
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        String dateTime="2017-07-07";//注：改正后这里前后也加了空格
        Date date= format.parse(dateTime);
        balanceDailyStatisticMapper.setBalanceElectrodeStatistic(date, 4, 4,4);
    }
    @Test
    public void getBalanceElectrodeInfoOnedayFromOriginalTest() throws ParseException {
        CustomerContextHolder.selectTestDataSource();
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        String dateTime="2017-07-06";//注：改正后这里前后也加了空格
        Date date= format.parse(dateTime);
        List<Integer> electrodeList = new ArrayList<>();
        electrodeList.add(0);
        electrodeList.add(4);
        electrodeList.add(8);
        List<Integer> returnList = new ArrayList<>();
        List<BalanceElectrodeModel> list = new ArrayList<>();
        list.addAll(balanceDailyStatisticMapper.getBalanceElectrodeInfoOnedayFromOriginal(date, 1));
        for (BalanceElectrodeModel item : list) {
            returnList.add(item.getElectrodeNumber());
        }
        electrodeList.removeAll(returnList);
        for (int item:electrodeList) {
            list.add(new BalanceElectrodeModel(dateTime,item,0));
        }
        for (BalanceElectrodeModel item : list) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void getBalanceElectrodeInfoDayFrameTest() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2017-07-08");
        balanceDailyStatisticMapper.getBalanceElectrodeInfoDayFrame(date, 2);
    }

    @Test
    public void deleteBalance24HourCountTest() {
        balanceDailyStatisticMapper.deleteBalance24HourCount();
    }

    @Test
    public void getBalanceMaxMinDatesTest() {
        BalanceMaxMinDatesModel balanceMaxMinDatesModel = new BalanceMaxMinDatesModel();
        CustomerContextHolder.selectProdDataSource();
        balanceMaxMinDatesModel = balanceDailyStatisticMapper.getBalanceMaxMinDates(1);
        System.out.println(balanceMaxMinDatesModel.toString());
    }

}

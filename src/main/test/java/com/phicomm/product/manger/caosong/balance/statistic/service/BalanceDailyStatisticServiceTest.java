package com.phicomm.product.manger.caosong.balance.statistic.service;

import com.phicomm.product.manger.model.statistic.Balance24HourDisplayQueryResultModel;
import com.phicomm.product.manger.model.statistic.BalanceElectrodeModel;
import com.phicomm.product.manger.model.statistic.BalanceElectrodeQueryResultModel;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import com.phicomm.product.manger.service.BalanceDailyStatisticService;
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
 * Created by song02.cao on 2017/11/17.
 */


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/spring-root.xml"})
public class BalanceDailyStatisticServiceTest {
    @Autowired
    BalanceDailyStatisticService balanceDailyStatisticService;

    @Test
    public void test() {
        CustomerContextHolder.selectTestDataSource();
        balanceDailyStatisticService.setBalance24HourCount();
    }

    @Test
    public void setBalanceElectrodeStatisticOneDayTest() {

        balanceDailyStatisticService.setBalanceElectrodeStatisticOneDay("2017-09-22");
    }
    @Test
    public void getBalanceElectrodeStatisticTest() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2017-07-15");
        BalanceElectrodeQueryResultModel balanceElectrodeQueryResultModel = new BalanceElectrodeQueryResultModel();
        balanceElectrodeQueryResultModel = balanceDailyStatisticService.get20DaysBalanceElectrodeStatistic(date);
        System.out.println(balanceElectrodeQueryResultModel.toString());
    }
    @Test
    public void setBalance24HourCount2Test() {
        balanceDailyStatisticService.setBalance24HourCount();
    }

    @Test
    public void getBalance24HourCountTest() {
        Balance24HourDisplayQueryResultModel balance24HourDisplayQueryResultModel= balanceDailyStatisticService.getBalance24HourCount();
        System.out.println(balance24HourDisplayQueryResultModel.toString());
    }

    @Test
    public void setBalanceElectrodeStatisticFromAllHistoryTest() {
        balanceDailyStatisticService.setBalanceElectrodeStatisticFromAllHistory();
    }
}

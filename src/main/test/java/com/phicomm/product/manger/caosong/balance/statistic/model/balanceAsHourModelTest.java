package com.phicomm.product.manger.caosong.balance.statistic.model;

import com.phicomm.product.manger.model.statistic.BalanceAsHourModel;
import com.phicomm.product.manger.module.dds.CustomerContextHolder;
import org.junit.Test;

/**
 * Created by song02.cao on 2017/11/16.
 */
public class balanceAsHourModelTest {

    @Test
    public void test() {

        CustomerContextHolder.selectTestDataSource();
        BalanceAsHourModel balanceAsHourModel = new BalanceAsHourModel();
        balanceAsHourModel.setTimeAsHour("2017");
        balanceAsHourModel.setCount(10);
        System.out.println("setTimeAsHour = " + balanceAsHourModel.getTimeAsHour());
        System.out.println("setCount = " + balanceAsHourModel.getCount());
        System.out.println("toString : " + balanceAsHourModel.toString());

    }
}

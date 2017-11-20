package com.phicomm.product.manger.caosong.balance.statistic.model;

import com.phicomm.product.manger.model.statistic.Balance24HourDisplayQueryResultModel;
import org.junit.Test;

/**
 * Created by song02.cao on 2017/11/20.
 */
public class Balance24HourDisplayQueryResultModelTest {
    @Test
    public void test() {
        Balance24HourDisplayQueryResultModel balance24HourDisplayQueryResultModel = new Balance24HourDisplayQueryResultModel();
        int[] counts = {1, 2, 3, 4};
        String[] hours = {"1", "2", "3", "4"};
        balance24HourDisplayQueryResultModel.setCounts(counts);
        balance24HourDisplayQueryResultModel.setHours(hours);
        System.out.println(balance24HourDisplayQueryResultModel.toString());

    }
}

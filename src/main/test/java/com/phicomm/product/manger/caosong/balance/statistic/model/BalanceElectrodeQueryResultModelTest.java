package com.phicomm.product.manger.caosong.balance.statistic.model;

import com.phicomm.product.manger.model.statistic.BalanceElectrodeQueryResultModel;
import org.junit.Test;

/**
 * Created by song02.cao on 2017/11/21.
 */
public class BalanceElectrodeQueryResultModelTest {
    @Test
    public void test() {
        BalanceElectrodeQueryResultModel balanceElectrodeQueryResultModel = new BalanceElectrodeQueryResultModel();
        String[] dates = {"2017-07-07", "2017-07-08", "2017-07-09"};
        int[] electrode0 = {0, 0, 0};
        int[] electrode4 = {4, 4, 4};
        int[] electrode8 = {8, 8, 8};
        balanceElectrodeQueryResultModel.setDates(dates);
        balanceElectrodeQueryResultModel.setElectrode0Counts(electrode0);
        balanceElectrodeQueryResultModel.setElectrode4Counts(electrode4);
        balanceElectrodeQueryResultModel.setElectrode8Counts(electrode8);
        System.out.println(balanceElectrodeQueryResultModel.toString());
    }
}

package com.phicomm.product.manger.caosong.balance.statistic.model;

import com.phicomm.product.manger.model.statistic.BalanceMaxMinDatesModel;
import org.junit.Test;

/**
 * Created by song02.cao on 2017/11/22.
 */
public class BalanceMaxMinDatesModelTest {
    @Test
    public void test() {
        BalanceMaxMinDatesModel balanceMaxMinDatesModel = new BalanceMaxMinDatesModel();
        balanceMaxMinDatesModel.setMaxDate("2017-11-22");
        balanceMaxMinDatesModel.setMinDate("2017-10-22");
        System.out.println(balanceMaxMinDatesModel.toString());
    }
}

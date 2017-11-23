package com.phicomm.product.manger.caosong.balance.statistic.model;

import com.phicomm.product.manger.model.statistic.BalanceElectrodeModel;
import org.junit.Test;

/**
 * Created by song02.cao on 2017/11/20.
 */
public class BalanceElectrodeModeTest {
    @Test
    public void test() {
        BalanceElectrodeModel balanceElectrodeModel = new BalanceElectrodeModel();
        balanceElectrodeModel.setDate("2017-01-23");
        balanceElectrodeModel.setElectrodeNumber(8);
        balanceElectrodeModel.setCounts(10);
        System.out.println(balanceElectrodeModel.toString());
    }
}

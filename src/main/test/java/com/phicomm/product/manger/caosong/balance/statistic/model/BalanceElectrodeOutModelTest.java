package com.phicomm.product.manger.caosong.balance.statistic.model;

import com.phicomm.product.manger.model.statistic.BalanceElectrodeOutModel;
import org.junit.Test;

/**
 * Created by song02.cao on 2017/11/21.
 */
public class BalanceElectrodeOutModelTest {
    @Test
    public void test() {
        BalanceElectrodeOutModel balanceElectrodeOutModel = new BalanceElectrodeOutModel();
        balanceElectrodeOutModel.setDate("2017-07-07");
        balanceElectrodeOutModel.setElectrode0Count(10);
        balanceElectrodeOutModel.setElectrode4Count(40);
        balanceElectrodeOutModel.setElectrode8Count(80);
        System.out.println(balanceElectrodeOutModel.toString());
    }
}

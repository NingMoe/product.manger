package com.phicomm.product.manger.model.statistic;

/**
 * pv & uv
 *
 * @author yufei.liu
 */
public class BalanceActiveStatisticModel {

    private long pv;

    private long uv;

    public BalanceActiveStatisticModel(long pv, long uv) {
        this.pv = pv;
        this.uv = uv;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    public long getUv() {
        return uv;
    }

    public void setUv(long uv) {
        this.uv = uv;
    }
}

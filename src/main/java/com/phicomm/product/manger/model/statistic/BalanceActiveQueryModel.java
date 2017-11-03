package com.phicomm.product.manger.model.statistic;

/**
 * 日期和数量
 *
 * @author yufei.liu
 */
public class BalanceActiveQueryModel {

    private String date;

    private int activeCountPv;

    private int activeCountUv;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getActiveCountPv() {
        return activeCountPv;
    }

    public void setActiveCountPv(int activeCountPv) {
        this.activeCountPv = activeCountPv;
    }

    public int getActiveCountUv() {
        return activeCountUv;
    }

    public void setActiveCountUv(int activeCountUv) {
        this.activeCountUv = activeCountUv;
    }
}

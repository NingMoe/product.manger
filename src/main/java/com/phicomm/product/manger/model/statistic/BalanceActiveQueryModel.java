package com.phicomm.product.manger.model.statistic;

/**
 * 日期和数量
 *
 * @author yufei.liu
 */
public class BalanceActiveQueryModel {

    private String date;

    private int activeCount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }
}

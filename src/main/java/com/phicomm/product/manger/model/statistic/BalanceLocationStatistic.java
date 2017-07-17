package com.phicomm.product.manger.model.statistic;

import java.util.List;

/**
 * 电子秤地区分布
 * <p>
 * Created by yufei.liu on 2017/6/25.
 */
public class BalanceLocationStatistic {

    private int locationTotalCount;

    private List<BalanceLocation> totalStatistic;

    private List<BalanceLocation> currentDateStatistic;

    public int getLocationTotalCount() {
        return locationTotalCount;
    }

    public void setLocationTotalCount(int locationTotalCount) {
        this.locationTotalCount = locationTotalCount;
    }

    public List<BalanceLocation> getTotalStatistic() {
        return totalStatistic;
    }

    public void setTotalStatistic(List<BalanceLocation> totalStatistic) {
        this.totalStatistic = totalStatistic;
    }

    public List<BalanceLocation> getCurrentDateStatistic() {
        return currentDateStatistic;
    }

    public void setCurrentDateStatistic(List<BalanceLocation> currentDateStatistic) {
        this.currentDateStatistic = currentDateStatistic;
    }

    @Override
    public String toString() {
        return "BalanceLocationStatistic{" +
                "locationTotalCount=" + locationTotalCount +
                ", totalStatistic=" + totalStatistic +
                ", currentDateStatistic=" + currentDateStatistic +
                '}';
    }
}

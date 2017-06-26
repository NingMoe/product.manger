package com.phicomm.product.manger.model.statistic;

import java.util.List;

/**
 * 电子秤地区分布
 * <p>
 * Created by yufei.liu on 2017/6/25.
 */
public class BalanceLocationStatistic {

    private List<BalanceLocation> totalStatistic;

    private List<BalanceLocation> currentDateStatistic;

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
                "totalStatistic=" + totalStatistic +
                ", currentDateStatistic=" + currentDateStatistic +
                '}';
    }
}

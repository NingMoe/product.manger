package com.phicomm.product.manger.model.statistic;

/**
 * Created by song02.cao on 2017/11/16.
 */

public class BalanceAsHourModel {
    private String timeAsHour;
    private int count;

    public String getTimeAsHour() {
        return this.timeAsHour;
    }

    public void setTimeAsHour(String timeAsHour) {
        this.timeAsHour = timeAsHour;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BalanceAsHourModel = {" + "\n" +
                "      timeAsHour = " + timeAsHour + ",\n" +
                "      count = " + count + ",\n" +
                "}";
    }

}
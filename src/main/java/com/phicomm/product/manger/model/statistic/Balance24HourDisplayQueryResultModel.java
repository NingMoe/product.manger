package com.phicomm.product.manger.model.statistic;

import java.util.Arrays;

/**
 * Created by song02.cao on 2017/11/20.
 */
public class Balance24HourDisplayQueryResultModel {
    String[] hours;
    int[] counts;

    public String[] getHours() {
        return this.hours;
    }

    public void setHours(String[] hours) {
        this.hours = hours;
    }

    public int[] getCounts() {
        return this.counts;
    }

    public void setCounts(int[] counts) {
        this.counts = counts;
    }

    @Override
    public String toString() {
        return "Balance24HourDisplayQueryResultModel = {" + "\n" +
                "       hours = " + Arrays.toString(hours) + ",\n" +
                "       counts = " + Arrays.toString(counts) + ",\n" +
                "}";
    }
}

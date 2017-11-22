package com.phicomm.product.manger.model.statistic;

/**
 * Created by song02.cao on 2017/11/22.
 */
public class BalanceMaxMinDatesModel {
    String maxDate;
    String minDate;

    public String getMaxDate() {
        return this.maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public String getMinDate() {
        return this.minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    @Override
    public String toString() {
        return "BalanceMaxMinDatesModel = {" + "\n" +
                "   maxDate = " + maxDate + ",\n" +
                "   minDate = " + minDate + ",\n" +
                "}";
    }
}

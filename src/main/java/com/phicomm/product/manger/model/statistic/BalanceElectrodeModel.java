package com.phicomm.product.manger.model.statistic;

/**
 * Created by song02.cao on 2017/11/20.
 */
public class BalanceElectrodeModel {
    String date;
    int electrodeNumber;
    int counts;

    public BalanceElectrodeModel(){};
    public BalanceElectrodeModel(String date,int electrodeNumber,int counts) {
        this.date = date;
        this.electrodeNumber = electrodeNumber;
        this.counts = counts;
    }
    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getElectrodeNumber() {
        return this.electrodeNumber;
    }

    public void setElectrodeNumber(int electrodeNumber) {
        this.electrodeNumber = electrodeNumber;
    }

    public int getCounts() {
        return this.counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    @Override
    public String toString() {
        return "BalanceElectrodeModel = { " + "\n" +
                "   date = " + date + ",\n" +
                "   electrodeNumber = " + electrodeNumber + ",\n" +
                "   counts = " + counts + "\n" +
                "}";
    }
}

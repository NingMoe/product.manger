package com.phicomm.product.manger.model.statistic;

import java.util.Arrays;

/**
 * Created by song02.cao on 2017/11/21.
 */
public class BalanceElectrodeQueryResultModel {
    private String[] dates;
    private int[] electrode0Counts;
    private int[] electrode4Counts;
    private int[] electrode8Counts;

    public String[] getDates() {
        return this.dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }

    public int[] getElectrode0Counts() {
        return this.electrode0Counts;
    }

    public void setElectrode0Counts(int[] electrode0Counts) {
        this.electrode0Counts = electrode0Counts;
    }

    public int[] getElectrode4Counts() {
        return this.electrode4Counts;
    }

    public void setElectrode4Counts(int[] electrode4Counts) {
        this.electrode4Counts = electrode4Counts;
    }

    public int[] getElectrode8Counts() {
        return this.electrode8Counts;
    }

    public void setElectrode8Counts(int[] electrode8Counts) {
        this.electrode8Counts = electrode8Counts;
    }

    @Override
    public String toString() {
        return "BalanceElectrodeQueryResultModel = { " + "\n" +
                "   dates = " + Arrays.toString(dates) + ",\n" +
                "   electrode0Counts = " + Arrays.toString(electrode0Counts) + ",\n" +
                "   electrode4Counts = " + Arrays.toString(electrode4Counts) + ",\n" +
                "   electrode8Counts = " + Arrays.toString(electrode8Counts) + ",\n" +
                "}";
    }
}

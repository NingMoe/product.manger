package com.phicomm.product.manger.model.statistic;

/**
 * Created by song02.cao on 2017/11/21.
 */
public class BalanceElectrodeOutModel {
    private String date;
    private int electrode0Count;
    private int electrode4Count;
    private int electrode8Count;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getElectrode0Count() {
        return this.electrode0Count;
    }

    public void setElectrode0Count(int electrode0Count) {
        this.electrode0Count = electrode0Count;
    }
    public int getElectrode4Count() {
        return this.electrode4Count;
    }

    public void setElectrode4Count(int electrode4Count) {
        this.electrode4Count = electrode4Count;
    }
    public int getElectrode8Count() {
        return this.electrode8Count;
    }

    public void setElectrode8Count(int electrode8Count) {
        this.electrode8Count = electrode8Count;
    }

    @Override
    public String toString() {
        return "BalanceElectrodeOutModel = { " + "\n" +
                "   date = " + date + ",\n" +
                "   electrode0Count = " + electrode0Count + ",\n" +
                "   electrode4Count = " + electrode4Count + ",\n" +
                "   electrode8Count = " + electrode8Count + ",\n" +
                "}";
    }
}

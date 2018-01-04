package com.phicomm.product.manger.model.terminal;

/**
 * @author wei.yang on 2018/1/1
 */
public class HistoryResultEntity extends BaseResultEntity{

    private String historyCompareObject;

    private int historyCount;

    private double percent;

    public String getHistoryCompareObject() {
        return historyCompareObject;
    }

    public void setHistoryCompareObject(String historyCompareObject) {
        this.historyCompareObject = historyCompareObject;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getHistoryCount() {
        return historyCount;
    }

    public void setHistoryCount(int historyCount) {
        this.historyCount = historyCount;
    }

    @Override
    public String toString() {
        return "HistoryResultEntity{" +
                "historyCompareObject='" + historyCompareObject + '\'' +
                ", historyCount=" + historyCount +
                ", percent=" + percent +
                '}';
    }
}

package com.phicomm.product.manger.model.bpm;

/**
 *
 * Created by yafei.hou on 2017/11/8.
 */
public class BpmMeasureBean {

    private int measureCount;

    private String measureTime;

    public BpmMeasureBean() {
    }

    public BpmMeasureBean(String measureTime, int measureCount) {
        this.measureCount = measureCount;
        this.measureTime = measureTime;
    }

    public int getMeasureCount() {
        return measureCount;
    }

    public void setMeasureCount(int measureCount) {
        this.measureCount = measureCount;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(String measureTime) {
        this.measureTime = measureTime;
    }

    @Override
    public String toString() {
        return "BpmMeasureBean{" +
                "measureCount=" + measureCount +
                ", measureTime='" + measureTime + '\'' +
                '}';
    }
}
